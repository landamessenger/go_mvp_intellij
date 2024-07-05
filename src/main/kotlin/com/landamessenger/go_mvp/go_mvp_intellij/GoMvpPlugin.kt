package com.landamessenger.go_mvp.go_mvp_intellij

import com.intellij.icons.AllIcons
import com.intellij.ide.BrowserUtil
import com.landamessenger.go_mvp.go_mvp_intellij.components.*
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.ExecutorImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.files.FilesManagerImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.plugin.Plugin
import com.landamessenger.go_mvp.go_mvp_intellij.components.requester.PubDevPackageInfoRequester
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.*

class GoMvpPlugin : Plugin() {
    override val executor = ExecutorImpl()
    override val filesManager = FilesManagerImpl()

    override suspend fun ui() {
        /**
         * If go_mvp not installed, alert and offer opening the documentation
         */
        val projectFileContent = filesManager.readFile(pubSpecFile)
        if (!projectFileContent.dependencyInstalled()) {
            val result = confirmation(
                title = "$id - Dependency Not Installed",
                message = "Install the library and try again.",
                okText = okText,
                cancelText = openDocumentationText,
                icon = AllIcons.General.Error
            )
            if (!result) {
                BrowserUtil.open(installationUrl)
            }
            return
        }

        /**
         * Wrong configuration, alert and offer opening the documentation
         */
        val pubSpec = filesManager.parseYaml(pubSpecFile)
        if (!pubSpec.correct()) {
            val result = confirmation(
                title = "$id - Wrong Configuration",
                message = "Error parsing $pubSpecFile. Check the current configuration.",
                okText = okText,
                cancelText = openDocumentationText,
                icon = AllIcons.General.ErrorDialog
            )
            if (!result) {
                BrowserUtil.open(installationUrl)
            }
            return
        }

        PubDevPackageInfoRequester("go_mvp")()?.let { dependency ->
            infoMessage(
                title = "$id - Latest version",
                message = "Last version found: ${dependency.latestVersion}",
            )
        }

        val actingPath = pubSpec?.pathForWorking() ?: return

        val projectPath = file.projectPath(pubSpec.go_mvp.baseProjectFolder)
        if (!projectPath.endsWith(actingPath)) {
            val continueAction = confirmation(
                title = "$id - Confirm New Screen Creation",
                message = "The directory in which you want to create the new MVP screen " +
                        "(${projectPath}) does not match the one defined in the configuration of go_mvp in the pubspec.yaml" +
                        " ($actingPath). Do you want to continue?",
                okText = continueText,
                cancelText = cancelText,
                icon = AllIcons.General.Warning
            )
            if (!continueAction) {
                return
            }
        }

        val input = project.input() ?: return

        executor.execute(
            listOf(
                "source ~/.zshrc",
                "cd ${project.basePath}",
                "dart run $id:create_screen $input"
            )
        )
    }
}

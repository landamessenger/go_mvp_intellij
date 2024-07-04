package com.landamessenger.go_mvp.go_mvp_intellij

import com.intellij.ide.BrowserUtil
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.ExecutorImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.files.FilesManagerImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.id
import com.landamessenger.go_mvp.go_mvp_intellij.components.installationUrl
import com.landamessenger.go_mvp.go_mvp_intellij.components.plugin.Plugin
import com.landamessenger.go_mvp.go_mvp_intellij.components.pubSpecFile
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.dependencyInstalled
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.input
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.pathForWorking


class GoMvpPlugin : Plugin() {
    override val executor = ExecutorImpl()
    override val filesManager = FilesManagerImpl()

    override fun main() {
        val input = project.input() ?: return

        /**
         * If go_mvp not installed, alert and open documentation
         */
        val projectFileContent = filesManager.readFile(pubSpecFile)
        if (!projectFileContent.dependencyInstalled()) {
            errorMessage("$id not installed on project.")
            BrowserUtil.open(installationUrl)
            return
        }

        val pubSpec = filesManager.parseYaml(pubSpecFile)
        if (pubSpec == null) {
            errorMessage("Error parsing $pubSpecFile")
            return
        }

        val actingPath = pubSpec.pathForWorking()

        executor.execute(
            listOf(
                "source ~/.zshrc",
                "cd ${project.basePath}",
                "dart run $id:create_screen $input"
            )
        )
    }
}

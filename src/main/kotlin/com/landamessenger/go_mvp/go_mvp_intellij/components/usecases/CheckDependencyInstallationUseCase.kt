package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.intellij.icons.AllIcons
import com.intellij.ide.BrowserUtil
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID
import com.landamessenger.go_mvp.go_mvp_intellij.components.INSTALLATION_URL
import com.landamessenger.go_mvp.go_mvp_intellij.components.OK_TEXT
import com.landamessenger.go_mvp.go_mvp_intellij.components.OPEN_DOCUMENTATION_TEXT
import com.landamessenger.go_mvp.go_mvp_intellij.components.PUB_SPEC_FILE
import com.landamessenger.go_mvp.go_mvp_intellij.components.files.FilesManagerImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.messages.confirmation
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.dependencyInstalled

class CheckDependencyInstallationUseCase : FilesUseCase<Boolean> {
    override val filesManager = FilesManagerImpl
    override suspend fun invoke(): Boolean {
        val projectFileContent = filesManager.readFile(PUB_SPEC_FILE)
        if (!projectFileContent.dependencyInstalled()) {
            val result = confirmation(
                title = "$ID - Dependency Not Installed",
                message = "Install the library and try again.",
                okText = OK_TEXT,
                cancelText = OPEN_DOCUMENTATION_TEXT,
                icon = AllIcons.General.Error
            )
            if (!result) {
                BrowserUtil.open(INSTALLATION_URL)
            }
            return result
        }
        return true
    }
}

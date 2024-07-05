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
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.PubSpec
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.correct

class CheckPubSpecUseCase : FilesUseCase<PubSpec?> {
    override val filesManager = FilesManagerImpl

    override suspend fun invoke(): PubSpec? {
        val pubSpec = filesManager.parseYaml(PUB_SPEC_FILE)
        if (!pubSpec.correct()) {
            val result = confirmation(
                title = "$ID - Wrong Configuration",
                message = "Error parsing $PUB_SPEC_FILE. Check the current configuration.",
                okText = OK_TEXT,
                cancelText = OPEN_DOCUMENTATION_TEXT,
                icon = AllIcons.General.ErrorDialog
            )
            if (!result) {
                BrowserUtil.open(INSTALLATION_URL)
            }
            return null
        }
        return pubSpec
    }
}

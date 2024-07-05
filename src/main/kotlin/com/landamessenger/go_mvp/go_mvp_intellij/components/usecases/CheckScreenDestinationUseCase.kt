package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.intellij.icons.AllIcons
import com.intellij.openapi.vfs.VirtualFile
import com.landamessenger.go_mvp.go_mvp_intellij.components.CANCEL_TEXT
import com.landamessenger.go_mvp.go_mvp_intellij.components.CONTINUE_TEXT
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID
import com.landamessenger.go_mvp.go_mvp_intellij.components.PUB_SPEC_FILE
import com.landamessenger.go_mvp.go_mvp_intellij.components.messages.confirmation
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.pathForWorking
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.projectPath

class CheckScreenDestinationUseCase(private val file: VirtualFile) : UseCase<Boolean> {
    override suspend fun invoke(): Boolean {
        val pubSpec = CheckPubSpecUseCase()() ?: return false

        val actingPath = pubSpec.pathForWorking()

        val projectPath = file.projectPath(pubSpec.go_mvp.baseProjectFolder)
        if (!projectPath.endsWith(actingPath)) {
            val continueAction = confirmation(
                title = "$ID - Confirm New Screen Creation",
                message = "The directory in which you want to create the new MVP screen " +
                        "(${projectPath}) does not match the one defined in the configuration of $ID in " +
                        "the $PUB_SPEC_FILE ($actingPath). Do you want to continue?",
                okText = CONTINUE_TEXT,
                cancelText = CANCEL_TEXT,
                icon = AllIcons.General.Warning
            )
            return continueAction
        }
        return true
    }
}

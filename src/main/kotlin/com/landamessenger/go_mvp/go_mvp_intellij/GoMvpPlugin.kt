package com.landamessenger.go_mvp.go_mvp_intellij

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.landamessenger.go_mvp.go_mvp_intellij.components.id
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.*


class GoMvpPlugin : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        kotlin.runCatching {
            val file: VirtualFile = event.file() ?: return@runCatching
            val project: Project = event.project() ?: return@runCatching
            val input = project.input() ?: return@runCatching

            val ttyConnector = project.createWindow()

            val commands = listOf(
                "source ~/.zshrc",
                "cd ${project.basePath}",
                "dart run $id:create_screen $input"
            )

            ttyConnector.execute(commands)
        }.onFailure { e ->
            Messages.showMessageDialog(
                "An Exception happened: ${e.message}",
                id,
                Messages.getErrorIcon()
            )
        }
    }

    override fun update(event: AnActionEvent) {
        val file = event.getData(CommonDataKeys.VIRTUAL_FILE)
        event.presentation.isEnabledAndVisible = file != null && file.isDirectory
    }
}

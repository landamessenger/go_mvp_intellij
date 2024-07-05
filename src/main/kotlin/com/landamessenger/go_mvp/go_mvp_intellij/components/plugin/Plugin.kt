package com.landamessenger.go_mvp.go_mvp_intellij.components.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageDialogBuilder
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.landamessenger.go_mvp.go_mvp_intellij.components.coroutines.CoroutineUtil
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.Executor
import com.landamessenger.go_mvp.go_mvp_intellij.components.files.FilesManager
import com.landamessenger.go_mvp.go_mvp_intellij.components.id
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.createWindow
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.file
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.project
import kotlinx.coroutines.launch
import javax.swing.Icon

abstract class Plugin : AnAction() {

    abstract val executor: Executor
    abstract val filesManager: FilesManager

    lateinit var file: VirtualFile
    lateinit var project: Project

    override fun actionPerformed(event: AnActionEvent) {
        kotlin.runCatching {
            CoroutineUtil.uiScope.launch {
                file = event.file() ?: return@launch
                project = event.project() ?: return@launch

                val ttyConnector = project.createWindow()

                filesManager.setup(project)
                executor.setup(ttyConnector)

                ui()
            }
        }.onFailure { e ->
            errorMessage(message = "An Exception happened: ${e.message}")
        }
    }

    override fun update(event: AnActionEvent) {
        val file = event.getData(CommonDataKeys.VIRTUAL_FILE)
        event.presentation.isEnabledAndVisible = file != null && file.isDirectory
    }

    abstract suspend fun ui()

    fun errorMessage(title: String = id, message: String) = Messages.showMessageDialog(
        message,
        title,
        Messages.getErrorIcon()
    )

    fun infoMessage(title: String = id, message: String) = Messages.showMessageDialog(
        message,
        title,
        Messages.getInformationIcon()
    )

    fun confirmation(
        title: String = id,
        message: String,
        okText: String = "OK",
        cancelText: String = "Cancel",
        icon: Icon? = null
    ) = when {
        icon != null -> MessageDialogBuilder
            .yesNo(
                title,
                message,
            )
            .yesText(okText)
            .noText(cancelText)
            .icon(icon)
            .guessWindowAndAsk()

        else -> MessageDialogBuilder
            .okCancel(
                id,
                message,
            )
            .yesText(okText)
            .noText(cancelText)
            .guessWindowAndAsk()
    }
}
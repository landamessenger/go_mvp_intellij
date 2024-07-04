package com.landamessenger.go_mvp.go_mvp_intellij.components.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.Executor
import com.landamessenger.go_mvp.go_mvp_intellij.components.files.FilesManager
import com.landamessenger.go_mvp.go_mvp_intellij.components.id
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.createWindow
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.file
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.project

abstract class Plugin : AnAction() {

    abstract val executor: Executor
    abstract val filesManager: FilesManager

    lateinit var file: VirtualFile
    lateinit var project: Project

    override fun actionPerformed(event: AnActionEvent) {
        kotlin.runCatching {
            file = event.file() ?: return@runCatching
            project = event.project() ?: return@runCatching

            val ttyConnector = project.createWindow()

            filesManager.setup(project)
            executor.setup(ttyConnector)

            main()
        }.onFailure { e ->
            errorMessage("An Exception happened: ${e.message}")
        }
    }

    override fun update(event: AnActionEvent) {
        val file = event.getData(CommonDataKeys.VIRTUAL_FILE)
        event.presentation.isEnabledAndVisible = file != null && file.isDirectory
    }

    abstract fun main()

    fun errorMessage(message: String) {
        Messages.showMessageDialog(
            message,
            id,
            Messages.getErrorIcon()
        )
    }

    fun infoMessage(message: String) {
        Messages.showMessageDialog(
            message,
            id,
            Messages.getInformationIcon()
        )
    }
}
package com.landamessenger.go_mvp.go_mvp_intellij.components.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.landamessenger.go_mvp.go_mvp_intellij.components.coroutines.CoroutineUtil
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.ExecutorImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.files.FilesManagerImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.messages.errorMessage
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.createWindow
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.file
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.project
import kotlinx.coroutines.launch

abstract class Plugin : AnAction() {

    private val executor = ExecutorImpl
    private val filesManager = FilesManagerImpl

    lateinit var file: VirtualFile
    lateinit var project: Project

    override fun actionPerformed(event: AnActionEvent) {
        kotlin.runCatching {
            CoroutineUtil.uiScope.launch {
                file = event.file() ?: return@launch
                project = event.project() ?: return@launch

                val terminalComponents = project.createWindow()

                filesManager.setup(project)
                executor.setup(project, terminalComponents)

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
}

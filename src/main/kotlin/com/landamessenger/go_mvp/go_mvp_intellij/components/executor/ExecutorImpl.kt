package com.landamessenger.go_mvp.go_mvp_intellij.components.executor

import com.intellij.openapi.project.Project
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.TerminalComponents
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.executeCommand

object ExecutorImpl : Executor {
    override lateinit var project: Project
    override lateinit var terminalComponents: TerminalComponents
    override fun setup(project: Project, terminalComponents: TerminalComponents) {
        this.project = project
        this.terminalComponents = terminalComponents
    }

    override suspend fun execute(command: String) = terminalComponents.connector.executeCommand(command)
}

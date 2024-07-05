package com.landamessenger.go_mvp.go_mvp_intellij.components.executor

import com.intellij.openapi.project.Project
import com.jediterm.terminal.TtyConnector
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.execute

object ExecutorImpl : Executor {
    override lateinit var project: Project
    override lateinit var connector: TtyConnector
    override fun setup(project: Project, connector: TtyConnector) {
        this.project = project
        this.connector = connector
    }

    override fun execute(commands: List<String>) {
        connector.execute(commands)
    }
}

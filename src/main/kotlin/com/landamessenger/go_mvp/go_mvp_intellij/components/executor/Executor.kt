package com.landamessenger.go_mvp.go_mvp_intellij.components.executor

import com.intellij.openapi.project.Project
import com.jediterm.terminal.TtyConnector

interface Executor {
    val project: Project
    val connector: TtyConnector
    fun setup(project: Project, connector: TtyConnector)
    fun execute(commands: List<String>)
}

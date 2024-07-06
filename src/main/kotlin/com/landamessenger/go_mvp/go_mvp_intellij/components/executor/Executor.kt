package com.landamessenger.go_mvp.go_mvp_intellij.components.executor

import com.intellij.openapi.project.Project
import com.jediterm.terminal.TtyConnector
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.OutputLimits
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.TerminalComponents

interface Executor {
    val project: Project
    val terminalComponents: TerminalComponents
    fun setup(project: Project, terminalComponents: TerminalComponents)
    suspend fun execute(command: String): OutputLimits
}

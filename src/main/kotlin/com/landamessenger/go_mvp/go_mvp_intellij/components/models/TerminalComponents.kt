package com.landamessenger.go_mvp.go_mvp_intellij.components.models

import com.intellij.terminal.JBTerminalWidget
import com.jediterm.terminal.TtyConnector

data class TerminalComponents(
    val terminal: JBTerminalWidget,
    val connector: TtyConnector,
)

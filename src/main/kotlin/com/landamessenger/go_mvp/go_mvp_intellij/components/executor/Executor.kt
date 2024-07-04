package com.landamessenger.go_mvp.go_mvp_intellij.components.executor

import com.jediterm.terminal.TtyConnector

interface Executor {
    val connector: TtyConnector
    fun setup(connector: TtyConnector)
    fun execute(commands: List<String>)
}

package com.landamessenger.go_mvp.go_mvp_intellij.components.executor

import com.jediterm.terminal.TtyConnector
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.execute

class ExecutorImpl : Executor {
    override lateinit var connector: TtyConnector
    override fun setup(connector: TtyConnector) {
        this.connector = connector
    }

    override fun execute(commands: List<String>) {
        connector.execute(commands)
    }
}

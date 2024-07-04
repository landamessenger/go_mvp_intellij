package com.landamessenger.go_mvp.go_mvp_intellij.extensions

import com.jediterm.terminal.TtyConnector

fun TtyConnector.execute(commands: List<String>) {
    commands.forEach {
        executeCommandOnPipe(it)
    }
}

private fun TtyConnector.executeCommandOnPipe(command: String) {
    write("$command\n")
}
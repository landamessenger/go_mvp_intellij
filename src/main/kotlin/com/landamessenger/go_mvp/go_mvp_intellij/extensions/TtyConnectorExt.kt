package com.landamessenger.go_mvp.go_mvp_intellij.extensions

import com.intellij.openapi.util.SystemInfo
import com.jediterm.terminal.TtyConnector
import com.landamessenger.go_mvp.go_mvp_intellij.components.COMMAND_DELAY
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.OutputLimits
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.MessageDigest

suspend fun TtyConnector.executeCommand(command: String): OutputLimits = coroutineScope {
    val id = command
        .replace(" ", "")
        .replace("%", "")
        .replace("echo", "")
        .lowercase()

    val outputLimits = OutputLimits(
        start = "${id}_start".sha256(),
        end = "${id}_end".sha256(),
    )
    launch {
        delay(COMMAND_DELAY)
        if (SystemInfo.isWindows) {
            write("echo ${outputLimits.start} && $command && echo ${outputLimits.end}\r")
        } else {
            write("echo ${outputLimits.start} && $command && echo ${outputLimits.end}\n")
        }
    }
    return@coroutineScope outputLimits
}

private fun String.sha256(): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(this.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

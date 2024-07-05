package com.landamessenger.go_mvp.go_mvp_intellij.extensions

import com.intellij.icons.AllIcons
import com.intellij.icons.ExpUiIcons
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.terminal.JBTerminalWidget
import com.intellij.terminal.pty.PtyProcessTtyConnector
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.jediterm.terminal.TtyConnector
import com.landamessenger.go_mvp.go_mvp_intellij.components.id
import com.pty4j.PtyProcessBuilder
import org.jetbrains.plugins.terminal.JBTerminalSystemSettingsProvider
import java.nio.charset.StandardCharsets

fun Project.createWindow(): TtyConnector {
    val settingsProvider = JBTerminalSystemSettingsProvider()
    val terminalWidget = JBTerminalWidget(
        this,
        80,
        24,
        settingsProvider,
        null,
        this
    )
    terminalWidget.terminalPanel.setCursorVisible(true)

    var ttyConnector = terminalWidget.ttyConnector
    if (ttyConnector == null) {
        ttyConnector = createTtyConnector()
        terminalWidget.ttyConnector = ttyConnector
    }
    terminalWidget.start()

    // Create a new tool window to host the terminal
    val toolWindowManager = ToolWindowManager.getInstance(this)
    val toolWindow = toolWindowManager.getToolWindow(id) ?: toolWindowManager.registerToolWindow(
        id,
        true,
        ToolWindowAnchor.BOTTOM
    )

    val content: Content = ContentFactory.getInstance().createContent(terminalWidget, "", false)
    toolWindow.contentManager.removeAllContents(true)
    toolWindow.contentManager.addContent(content)
    toolWindow.show()

    return ttyConnector
}

fun Project.input(): String? {
    val input: String? = Messages.showInputDialog(
        this,
        "Define a name for the new MVP screen.",
        "$id - New Screen",
        ExpUiIcons.Actions.Generated
    )
    return input
}

private fun createTtyConnector(): TtyConnector {
    try {
        var envs = System.getenv()
        val command: Array<String>
        /*
        if (UIUtil.isWindows) {
            command = arrayOf("cmd.exe")
        } else {*/
        command = arrayOf("/bin/bash", "--login")
        envs = HashMap(System.getenv())
        envs["TERM"] = "xterm-256color"
        //}

        val process = PtyProcessBuilder().setCommand(command).setEnvironment(envs).start()
        return PtyProcessTtyConnector(process, StandardCharsets.UTF_8)
    } catch (e: java.lang.Exception) {
        throw IllegalStateException(e)
    }
}
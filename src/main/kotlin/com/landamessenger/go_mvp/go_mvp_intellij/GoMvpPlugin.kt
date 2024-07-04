package com.landamessenger.go_mvp.go_mvp_intellij

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.ToolWindowAnchor
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.terminal.JBTerminalWidget
import com.intellij.terminal.pty.PtyProcessTtyConnector
import com.intellij.ui.content.Content
import com.intellij.ui.content.ContentFactory
import com.intellij.util.ui.UIUtil
import com.jediterm.terminal.TtyConnector
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.execute
import com.pty4j.PtyProcessBuilder
import org.jetbrains.plugins.terminal.JBTerminalSystemSettingsProvider
import java.nio.charset.StandardCharsets


class GoMvpPlugin : AnAction() {

    private val id = "go_mvp"

    override fun actionPerformed(event: AnActionEvent) {
        kotlin.runCatching {
            val file: VirtualFile? = event.getData(CommonDataKeys.VIRTUAL_FILE)
            if (file == null || !file.isDirectory) {
                Messages.showMessageDialog(
                    "You must select a directory",
                    id,
                    Messages.getInformationIcon()
                )
                return
            }

            val project: Project? = event.getData(PlatformDataKeys.PROJECT)
            if (project == null) {
                Messages.showMessageDialog(
                    "No project found",
                    id,
                    Messages.getErrorIcon()
                )
                return@runCatching
            }

            val queryTxt: String? = Messages.showInputDialog(
                project,
                "Input query to search in My Snippets",
                id,
                Messages.getInformationIcon()
            )

            if (queryTxt.isNullOrEmpty()) {
                Messages.showMessageDialog(
                    "Define a name for the new screen",
                    id,
                    Messages.getErrorIcon()
                )
                return@runCatching
            }

            createTerminalAndExecuteCommand(project, queryTxt);
        }.onFailure { e ->
            Messages.showMessageDialog(
                "An Exception happened: ${e.message}",
                id,
                Messages.getErrorIcon()
            )
        }
    }

    override fun update(event: AnActionEvent) {
        val file = event.getData(CommonDataKeys.VIRTUAL_FILE)
        event.presentation.isEnabledAndVisible = file != null && file.isDirectory
    }

    private fun createTerminalAndExecuteCommand(project: Project, name: String) {
        UIUtil.invokeLaterIfNeeded {
            val settingsProvider = JBTerminalSystemSettingsProvider()
            val terminalWidget = JBTerminalWidget(
                project,
                80,
                24,
                settingsProvider,
                null,
                project
            )
            terminalWidget.terminalPanel.setCursorVisible(true)

            var ttyConnector = terminalWidget.ttyConnector
            if (ttyConnector == null) {
                terminalWidget.writePlainMessage("Error: TtyConnector is null\n")

                ttyConnector = createTtyConnector()
                terminalWidget.ttyConnector = ttyConnector

                terminalWidget.start()
            }

            val commands = listOf(
                "source ~/.zshrc",
                "cd ${project.basePath}",
                "dart run $id:create_screen $name"
            )
            ttyConnector.execute(commands)

            // Create a new tool window to host the terminal
            val toolWindowManager = ToolWindowManager.getInstance(project)
            val toolWindow = toolWindowManager.getToolWindow(id) ?: toolWindowManager.registerToolWindow(
                id,
                true,
                ToolWindowAnchor.BOTTOM
            )

            val content: Content = ContentFactory.getInstance().createContent(terminalWidget, "", false)
            toolWindow.contentManager.removeAllContents(true)
            toolWindow.contentManager.addContent(content)
            toolWindow.show()
        }
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
}

package com.landamessenger.go_mvp.go_mvp_intellij.extensions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID

fun AnActionEvent.file(): VirtualFile? {
    val file: VirtualFile? = getData(CommonDataKeys.VIRTUAL_FILE)
    if (file == null || !file.isDirectory) {
        Messages.showMessageDialog(
            "You must select a directory",
            ID,
            Messages.getInformationIcon()
        )
    }
    return file
}

fun AnActionEvent.project(): Project? {
    val project: Project? = getData(PlatformDataKeys.PROJECT)
    if (project == null) {
        Messages.showMessageDialog(
            "No project found",
            ID,
            Messages.getErrorIcon()
        )
    }
    return project
}

package com.landamessenger.go_mvp.go_mvp_intellij.components.messages

import com.intellij.openapi.ui.MessageDialogBuilder
import com.intellij.openapi.ui.Messages
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID
import javax.swing.Icon

fun errorMessage(title: String = ID, message: String) = Messages.showMessageDialog(
    message,
    title,
    Messages.getErrorIcon()
)

fun infoMessage(title: String = ID, message: String) = Messages.showMessageDialog(
    message,
    title,
    Messages.getInformationIcon()
)

fun confirmation(
    title: String = ID,
    message: String,
    okText: String = "OK",
    cancelText: String = "Cancel",
    icon: Icon? = null
) = when {
    icon != null -> MessageDialogBuilder
        .yesNo(
            title,
            message,
        )
        .yesText(okText)
        .noText(cancelText)
        .icon(icon)
        .guessWindowAndAsk()

    else -> MessageDialogBuilder
        .okCancel(
            title,
            message,
        )
        .yesText(okText)
        .noText(cancelText)
        .guessWindowAndAsk()
}

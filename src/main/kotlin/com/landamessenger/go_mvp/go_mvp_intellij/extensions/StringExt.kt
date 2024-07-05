package com.landamessenger.go_mvp.go_mvp_intellij.extensions

import com.landamessenger.go_mvp.go_mvp_intellij.components.ID

fun String.dependencyInstalled(): Boolean = contains("$ID:")

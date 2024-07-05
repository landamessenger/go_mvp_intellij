package com.landamessenger.go_mvp.go_mvp_intellij.extensions

import com.intellij.openapi.vfs.VirtualFile

fun VirtualFile.projectPath(baseProjectFolder: String) = when {
    path.contains(baseProjectFolder) -> baseProjectFolder + path.substringAfter(baseProjectFolder)
    else -> path
}

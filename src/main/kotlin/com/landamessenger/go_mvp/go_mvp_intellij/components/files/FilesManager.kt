package com.landamessenger.go_mvp.go_mvp_intellij.components.files

import com.intellij.openapi.project.Project
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.PubSpec

interface FilesManager {
    val project: Project
    fun setup(project: Project)
    fun readFile(path: String): String
    fun parseYaml(path: String): PubSpec?
}

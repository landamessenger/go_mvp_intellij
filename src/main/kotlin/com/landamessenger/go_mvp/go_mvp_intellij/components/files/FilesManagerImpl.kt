package com.landamessenger.go_mvp.go_mvp_intellij.components.files

import com.intellij.openapi.project.Project
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.PubSpec
import org.yaml.snakeyaml.Yaml
import java.io.File

class FilesManagerImpl : FilesManager {
    override lateinit var project: Project

    override fun setup(project: Project) {
        this.project = project
    }

    override fun readFile(path: String): String {
        val file = File("${project.basePath}/$path")
        if (!file.exists()) {
            return ""
        }
        if (!file.isFile) {
            return ""
        }
        return file.readText()
    }

    override fun parseYaml(path: String): PubSpec? {
        return kotlin.runCatching {
            val file = File("${project.basePath}/$path")
            if (!file.exists()) {
                return null
            }
            val yaml = Yaml()
            return yaml.loadAs(file.inputStream().reader(), PubSpec::class.java)
        }.getOrNull()
    }
}
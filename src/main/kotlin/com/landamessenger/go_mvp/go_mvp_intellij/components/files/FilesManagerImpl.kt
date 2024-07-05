package com.landamessenger.go_mvp.go_mvp_intellij.components.files

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.PubSpec
import java.io.File

object FilesManagerImpl : FilesManager {
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
            val mapper = ObjectMapper(YAMLFactory())
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            return mapper.readValue(File("${project.basePath}/$path"), PubSpec::class.java)
        }.onFailure { e ->
            Messages.showMessageDialog(
                e.message,
                ID,
                Messages.getErrorIcon()
            )
        }.getOrNull()
    }
}

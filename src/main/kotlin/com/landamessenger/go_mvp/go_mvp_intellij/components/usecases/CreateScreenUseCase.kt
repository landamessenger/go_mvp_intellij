package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.openapi.vfs.VirtualFile
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.ExecutorImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.messages.infoMessage
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.input
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.projectPath
import kotlinx.coroutines.delay

class CreateScreenUseCase(private val file: VirtualFile) : ExecutorUseCase<Boolean> {
    override val executor = ExecutorImpl
    private val project: Project get() = executor.project

    override suspend fun invoke(): Boolean {
        val pubSpec = CheckPubSpecUseCase()() ?: return false
        val input = project.input() ?: return false

        listOfNotNull(
            if (SystemInfo.isWindows) "echo %PATH%" else "source ~/.zshrc",
            "cd ${project.basePath}",
            "C:\\Users\\\"Efra Espada\"\\flutter\\bin\\dart.bat run $ID:create_screen $input ${file.projectPath(pubSpec.go_mvp.baseProjectFolder)}"
        ).forEach {
            val result = ExecuteCommandUseCase(it)()

        }

        return true
    }
}

package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.ExecutorImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.input
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.projectPath

class CreateScreenUseCase(private val file: VirtualFile) : ExecutorUseCase<Boolean> {
    override val executor = ExecutorImpl
    private val project: Project get() = executor.project

    override suspend fun invoke(): Boolean {
        val pubSpec = CheckPubSpecUseCase()() ?: return false
        val input = project.input() ?: return false

        executor.execute(
            listOf(
                "source ~/.zshrc",
                "cd ${project.basePath}",
                "dart run $ID:create_screen $input ${file.projectPath(pubSpec.go_mvp.baseProjectFolder)}"
            )
        )

        return true
    }
}

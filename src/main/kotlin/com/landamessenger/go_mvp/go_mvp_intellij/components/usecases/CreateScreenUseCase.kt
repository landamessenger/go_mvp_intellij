package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.intellij.openapi.project.Project
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.ExecutorImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID
import com.landamessenger.go_mvp.go_mvp_intellij.extensions.input

class CreateScreenUseCase : ExecutorUseCase<Boolean> {
    override val executor = ExecutorImpl
    private val project: Project get() = executor.project

    override suspend fun invoke(): Boolean {
        val input = project.input() ?: return false

        executor.execute(
            listOf(
                "source ~/.zshrc",
                "cd ${project.basePath}",
                "dart run $ID:create_screen $input"
            )
        )

        return true
    }
}

package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.Executor

interface ExecutorUseCase<T> : UseCase<T> {
    val executor: Executor
}

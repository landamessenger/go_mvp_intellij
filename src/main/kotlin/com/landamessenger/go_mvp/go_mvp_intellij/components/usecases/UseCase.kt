package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

interface UseCase<T> {
    suspend operator fun invoke(): T
}

package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.landamessenger.go_mvp.go_mvp_intellij.components.files.FilesManager

interface FilesUseCase<T> : UseCase<T> {
    val filesManager: FilesManager
}

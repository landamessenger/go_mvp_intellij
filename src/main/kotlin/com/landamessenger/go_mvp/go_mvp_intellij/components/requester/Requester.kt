package com.landamessenger.go_mvp.go_mvp_intellij.components.requester

import io.ktor.client.*

interface Requester<T> {
    val client: HttpClient
    suspend operator fun invoke() : T?
}
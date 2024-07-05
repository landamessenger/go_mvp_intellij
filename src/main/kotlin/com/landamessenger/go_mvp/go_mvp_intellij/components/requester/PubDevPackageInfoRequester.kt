package com.landamessenger.go_mvp.go_mvp_intellij.components.requester

import com.landamessenger.go_mvp.go_mvp_intellij.components.coroutines.CoroutineUtil
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.PubDevDependency
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class PubDevPackageInfoRequester(private val packageName: String) : Requester<PubDevDependency> {
    override val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend operator fun invoke(): PubDevDependency? = withContext(CoroutineUtil.ioScope.coroutineContext) {
        kotlin.runCatching {
            val response: HttpResponse = client.get("https://pub.dev/api/packages/$packageName")
            if (response.status.value == 200) response.body<PubDevDependency>() else null
        }.getOrNull()
    }
}
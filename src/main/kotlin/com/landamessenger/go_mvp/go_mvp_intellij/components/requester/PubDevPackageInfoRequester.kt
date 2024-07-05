package com.landamessenger.go_mvp.go_mvp_intellij.components.requester

import com.landamessenger.go_mvp.go_mvp_intellij.components.coroutines.CoroutineUtil
import com.landamessenger.go_mvp.go_mvp_intellij.components.models.PubDevDependency
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class PubDevPackageInfoRequester(private val packageName: String) : Requester<PubDevDependency> {
    companion object {
        private const val SUCCESS_CODE = 200
    }

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
            if (response.status.value == SUCCESS_CODE) response.body<PubDevDependency>() else null
        }.getOrNull()
    }
}

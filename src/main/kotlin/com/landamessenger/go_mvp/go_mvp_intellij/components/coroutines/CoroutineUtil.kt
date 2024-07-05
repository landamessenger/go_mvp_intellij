package com.landamessenger.go_mvp.go_mvp_intellij.components.coroutines

import com.intellij.util.concurrency.EdtExecutorService
import kotlinx.coroutines.*

object CoroutineUtil {
    private val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    private val job = SupervisorJob()
    private val uiDispatcher = EdtExecutorService.getInstance().asCoroutineDispatcher()
    private val ioDispatcher = Dispatchers.IO

    val uiScope = CoroutineScope(job + uiDispatcher + handler)
    val ioScope = CoroutineScope(job + ioDispatcher + handler)

    fun cancelAll() {
        job.cancel()
    }
}
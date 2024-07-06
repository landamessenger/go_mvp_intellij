package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.ExecutorImpl
import com.landamessenger.go_mvp.go_mvp_intellij.components.messages.infoMessage
import kotlinx.coroutines.delay

class ExecuteCommandUseCase(private val command: String) : ExecutorUseCase<String> {
    override val executor = ExecutorImpl

    override suspend fun invoke(): String {
        val outputLimits = executor.execute(command)

        while (true) {
            delay(1000)
            if (executor.terminalComponents.terminal.text.contains("\n${outputLimits.end}")) {
                break
            }
        }

        val output = executor.terminalComponents.terminal.text
            .split("\n${outputLimits.start}").last()
            .split("\n${outputLimits.end}").first()

        infoMessage(message = output)
        return output
    }
}

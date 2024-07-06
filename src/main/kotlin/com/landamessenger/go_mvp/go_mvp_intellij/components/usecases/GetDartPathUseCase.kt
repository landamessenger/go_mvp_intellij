package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.landamessenger.go_mvp.go_mvp_intellij.components.WINDOWS_SEPARATOR
import com.landamessenger.go_mvp.go_mvp_intellij.components.WINDOWS_USER_PROFILE
import com.landamessenger.go_mvp.go_mvp_intellij.components.executor.ExecutorImpl

class GetDartPathUseCase : ExecutorUseCase<String> {
    override val executor = ExecutorImpl

    override suspend fun invoke(): String {
        val userProfilePath = ExecuteCommandUseCase("echo $WINDOWS_USER_PROFILE")()
            .split(WINDOWS_SEPARATOR)
            .joinToString(WINDOWS_SEPARATOR) {
                when {
                    it.contains(" ") -> "\"$it\""
                    else -> it
                }
            }

        val pathEnvironment = ExecuteCommandUseCase("echo %PATH%")()

        var flutterPath = ""
        pathEnvironment.split(";").forEach {
            if (it.contains("flutter${WINDOWS_SEPARATOR}bin")) {
                flutterPath = it
            }
        }

        return flutterPath.replace(WINDOWS_USER_PROFILE, userProfilePath) + "${WINDOWS_SEPARATOR}dart.bat"
    }
}

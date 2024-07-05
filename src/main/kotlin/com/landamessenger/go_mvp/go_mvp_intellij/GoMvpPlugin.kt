package com.landamessenger.go_mvp.go_mvp_intellij

import com.landamessenger.go_mvp.go_mvp_intellij.components.plugin.Plugin
import com.landamessenger.go_mvp.go_mvp_intellij.components.usecases.CheckDependencyInstallationUseCase
import com.landamessenger.go_mvp.go_mvp_intellij.components.usecases.CheckPubSpecUseCase
import com.landamessenger.go_mvp.go_mvp_intellij.components.usecases.CheckScreenDestinationUseCase
import com.landamessenger.go_mvp.go_mvp_intellij.components.usecases.CheckVersionUseCase
import com.landamessenger.go_mvp.go_mvp_intellij.components.usecases.CreateScreenUseCase

class GoMvpPlugin : Plugin() {

    override suspend fun ui() {
        /**
         * If go_mvp not installed, alert and offer opening the documentation
         */
        if (!CheckDependencyInstallationUseCase()()) return

        /**
         * Wrong configuration, alert and offer opening the documentation
         */
        CheckPubSpecUseCase()() ?: return

        /**
         * Check version. Let user decide what to do.
         */
        if (!CheckVersionUseCase()()) return

        /**
         * Check new screen destination. Let user decide what to do.
         */
        if (!CheckScreenDestinationUseCase(file)()) return

        /**
         * Ask for a screen name
         */
        CreateScreenUseCase()()
    }
}

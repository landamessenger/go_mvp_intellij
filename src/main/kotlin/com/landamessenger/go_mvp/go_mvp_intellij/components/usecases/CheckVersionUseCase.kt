package com.landamessenger.go_mvp.go_mvp_intellij.components.usecases

import com.intellij.ide.BrowserUtil
import com.landamessenger.go_mvp.go_mvp_intellij.components.CHANGELOG_URL
import com.landamessenger.go_mvp.go_mvp_intellij.components.ID
import com.landamessenger.go_mvp.go_mvp_intellij.components.messages.confirmation
import com.landamessenger.go_mvp.go_mvp_intellij.components.requester.PubDevPackageInfoRequester

class CheckVersionUseCase : UseCase<Boolean> {
    override suspend fun invoke(): Boolean {
        val pubSpec = CheckPubSpecUseCase()() ?: return false
        val currentVersion = pubSpec.dependencies.go_mvp.toString().replace("^", "")

        return PubDevPackageInfoRequester(ID)()?.let { dependency ->
            if (currentVersion == dependency.latestVersion.version) {
                return@let true
            }
            val result = !confirmation(
                title = "$ID - Update Available",
                message = "A newer version of $ID has been found (${dependency.latestVersion.version}). " +
                        "The current version is $currentVersion.\nDo you want to check the update?",
                cancelText = "No, continue the process",
                okText = "Check update"
            )
            if (!result) {
                BrowserUtil.open(CHANGELOG_URL)
            }
            return@let result
        } ?: true
    }
}

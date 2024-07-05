package com.landamessenger.go_mvp.go_mvp_intellij.components.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PubDevDependency(
    val name: String,
    @SerialName("latest") val latestVersion: LatestVersionInfo
)

@Serializable
data class LatestVersionInfo(
    val version: String
)

package com.landamessenger.go_mvp.go_mvp_intellij.components.models

data class GoMvpConfig(
    val baseProjectFolder: String,
    val outputFolder: String,
    val modelsFile: String,
)

data class PubSpec(
    val go_mvp: GoMvpConfig
)
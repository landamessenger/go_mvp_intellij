package com.landamessenger.go_mvp.go_mvp_intellij.components.models

data class GoMvpConfig(
    val baseProjectFolder: String = "",
    val outputFolder: String = "",
    val modelsFile: String = "",
)

data class Dependencies(
    val go_mvp: Any? = null,
    val `object`: Any? = null,
)

data class PubSpec(
    val go_mvp: GoMvpConfig = GoMvpConfig(),
    val dependencies: Dependencies = Dependencies()
)

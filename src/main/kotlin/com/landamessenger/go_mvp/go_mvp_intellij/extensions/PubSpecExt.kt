package com.landamessenger.go_mvp.go_mvp_intellij.extensions

import com.landamessenger.go_mvp.go_mvp_intellij.components.models.PubSpec

fun PubSpec.pathForWorking() = when {
    !correct() -> ""
    else -> "${go_mvp.baseProjectFolder}/${go_mvp.outputFolder}"
}

fun PubSpec?.dependencyInstalled() = this?.dependencies?.go_mvp.isDependencyValid()

fun PubSpec?.correct() = configurationDefined() && dependenciesInstalled()

private fun PubSpec?.configurationDefined() = this != null &&
        go_mvp.baseProjectFolder.isConfigurationValid() &&
        go_mvp.outputFolder.isConfigurationValid() &&
        go_mvp.modelsFile.isConfigurationValid()

private fun PubSpec?.dependenciesInstalled() = this != null &&
        dependencies.go_mvp.isDependencyValid() &&
        dependencies.`object`.isDependencyValid()

private fun String.isConfigurationValid() = trim().isNotEmpty()

private fun Any?.isDependencyValid() = this != null

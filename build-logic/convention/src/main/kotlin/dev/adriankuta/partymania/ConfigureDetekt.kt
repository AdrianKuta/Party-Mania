package dev.adriankuta.partymania

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import java.io.FileWriter

internal fun Project.configureDetektDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        "detektPlugins"(libs.findLibrary("detekt.ktlint").get())
        "detektPlugins"(libs.findLibrary("detekt.compose").get())
    }
}

internal fun Project.configureDetektForNonUiModule() =
    createDetektConfigFile(NonDefault.trimIndent())

internal fun Project.configureDetektForComposeModuleExceptions() =
    createDetektConfigFile(ComposeExceptions.trimIndent())

internal fun Project.createDetektConfigFile(deviationsFromDefaultConfig: String) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    if (!file(DetektConfigPath).exists()) {
        val detektConfigFile = project.file(DetektConfigPath)
        detektConfigFile.parentFile.mkdirs()
        detektConfigFile.createNewFile()
        val writer = FileWriter(detektConfigFile)
        writer.write(deviationsFromDefaultConfig)
        writer.close()
    }

    configure<DetektExtension> {
        toolVersion = libs.findVersion("detekt").get().toString()
        config.setFrom(files(DetektConfigPath))
        buildUponDefaultConfig = true
    }
}

private const val DetektConfigPath = "config/detekt/detekt.yml"

private val NonDefault = """
    # Deviations from defaults
    formatting:
      TrailingCommaOnCallSite:
        active: true
        autoCorrect: true
        useTrailingCommaOnCallSite: true
      TrailingCommaOnDeclarationSite:
        active: true
        autoCorrect: true
        useTrailingCommaOnDeclarationSite: true
"""

private val ComposeExceptions = """
    # Exceptions for compose. See https://detekt.dev/docs/introduction/compose
    naming:
      FunctionNaming:
        functionPattern: '[a-zA-Z][a-zA-Z0-9]*'
    
      TopLevelPropertyNaming:
        constantPattern: '[A-Z][A-Za-z0-9]*'
    
    complexity:
      LongParameterList:
        ignoreAnnotated: ['Composable']
      TooManyFunctions:
        ignoreAnnotatedFunctions: ['Preview']
    
    style:
      MagicNumber:
        ignorePropertyDeclaration: true
        ignoreCompanionObjectPropertyDeclaration: true
        ignoreAnnotated: ['Composable']
    
      UnusedPrivateMember:
        ignoreAnnotated: ['Composable']
""" + NonDefault

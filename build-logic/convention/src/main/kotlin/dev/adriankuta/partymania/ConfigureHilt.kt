package dev.adriankuta.partymania

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureHilt() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        "implementation"(libs.findLibrary("hilt.android").get())
        "ksp"(libs.findLibrary("hilt.compiler").get())

        // For instrumentation tests
        "androidTestImplementation"(libs.findLibrary("hilt.testing").get())
        "kspAndroidTest"(libs.findLibrary("hilt.compiler").get())

        // For local unit tests hilt-android-testing
        "testImplementation"(libs.findLibrary("hilt.testing").get())
        "kspTest"(libs.findLibrary("hilt.compiler").get())
    }
}

package dev.adriankuta.partymania

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureInstrumentation() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        "androidTestImplementation"(libs.findLibrary("truth").get())
        "androidTestImplementation"(libs.findLibrary("mockk.android").get())
        "androidTestImplementation"(libs.findLibrary("androidx.test.rules").get())
        "androidTestImplementation"(libs.findLibrary("androidx.test.uiautomator").get())
        //"androidTestImplementation"(libs.findLibrary("turbine").get())
    }
}

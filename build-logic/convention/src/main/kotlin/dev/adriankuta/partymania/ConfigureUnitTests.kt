package dev.adriankuta.partymania

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureUnitTests() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        "testImplementation"(libs.findLibrary("junit4").get())
        "testImplementation"(libs.findLibrary("androidx.test.core").get())
        "testImplementation"(libs.findLibrary("kotlinx.coroutines.test").get())
        "testImplementation"(libs.findLibrary("truth").get())
        "testImplementation"(libs.findLibrary("mockk.android").get())
        //"testImplementation"(libs.findLibrary("turbine").get())
    }
}

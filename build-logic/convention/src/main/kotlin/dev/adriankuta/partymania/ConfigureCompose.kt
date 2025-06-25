package dev.adriankuta.partymania

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.compose")
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
    }

    extensions.configure<ComposeCompilerGradlePluginExtension> {
        enableStrongSkippingMode = true
        includeSourceInformation = true
    }

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        "androidTestImplementation"(platform(bom))
        "androidTestImplementation"(libs.findLibrary("androidx.compose.ui.test.junit").get())

        "debugImplementation"(platform(bom))
        "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling").get())
        //"debugImplementation"(libs.findLibrary("androidx.compose.ui.testManifest").get())

        "implementation"(platform(bom))
        "implementation"(libs.findLibrary("androidx.compose.animation").get())
        "implementation"(libs.findLibrary("androidx.compose.material3").get())
        "implementation"(libs.findLibrary("androidx.compose.ui").get())
        "implementation"(libs.findLibrary("androidx.compose.ui.graphics").get())
        "implementation"(libs.findLibrary("androidx.compose.ui.tooling.preview").get())
        "implementation"(libs.findLibrary("androidx.lifecycle.runtime.compose").get())
        "implementation"(libs.findLibrary("androidx.navigation.compose").get())
        "implementation"(libs.findLibrary("kotlinx.collections.immutable").get())
    }
}

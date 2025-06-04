package dev.adriankuta.partymania

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureLibrary() {
    with(pluginManager) {
        apply("com.android.library")
        apply("io.gitlab.arturbosch.detekt")
        apply("org.jetbrains.kotlin.android")
        apply("org.gradle.jacoco")
    }

    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    val targetSdk = libs.findVersion("targetSdk").get().toString().toInt()

    // android block
    extensions.configure<LibraryExtension> {
        configureKotlinAndroid(this)
        defaultConfig.targetSdk = targetSdk
        defaultConfig.consumerProguardFiles("consumer-rules.pro")

        buildTypes {
            release {
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro",
                )
            }
            debug {
                testCoverage {
                    enableUnitTestCoverage =
                        false // Test coverage spanks memory on the CI with ye older AGP
                    enableAndroidTestCoverage = false
                }
            }
        }

        configureFlavors(this)
        configureAndroidLint(this)
//                configureGradleManagedDevices(this)
    }

//            val extension = extensions.getByType<LibraryAndroidComponentsExtension>()
//            configureJacoco(extension)
    configureDetektDependencies()
    //configureSonar()
    configureUnitTests()

    extensions.configure<KotlinAndroidProjectExtension> {
        //explicitApi()
    }
}
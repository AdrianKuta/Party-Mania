/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.android.build.api.dsl.ApplicationExtension
import dev.adriankuta.partymania.configureAndroidLint
import dev.adriankuta.partymania.configureDetektDependencies
import dev.adriankuta.partymania.configureFlavors
import dev.adriankuta.partymania.configureKotlinAndroid
import dev.adriankuta.partymania.configureUnitTests
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

@Suppress("unused") // This is called as a string in the gradle plugin block
class AndroidApplicationConvention : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("io.gitlab.arturbosch.detekt")
                apply("org.jetbrains.kotlin.android")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            // android block
            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                defaultConfig.vectorDrawables {
                    useSupportLibrary = true
                }

                buildTypes {
                    debug {
                        testCoverage {
                            enableUnitTestCoverage =
                                false // Test coverage spanks memory on the CI with ye older AGP
                            enableAndroidTestCoverage = false
                        }
                        applicationIdSuffix = ".debug"
                    }
                }

                configureFlavors(this)
                configureAndroidLint(this)
//                configureGradleManagedDevices(this)
            }
//            val extension = extensions.getByType<ApplicationAndroidComponentsExtension>()
//            configureJacoco(extension)
            configureDetektDependencies()
            //configureSonar()
            configureUnitTests()
        }

    }

}

import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21

/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    `kotlin-dsl`
}

group = "dev.adriankuta.partymania.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget = JVM_21
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    /**
     * Register convention plugins so they are available in the build scripts of the application
     */

    plugins {
        register("androidLibrary") {
            id = "partymania.android.library"
            implementationClass = "AndroidLibraryConvention"
        }
        register("androidLibraryCompose") {
            id = "partymania.android.library.compose"
            implementationClass = "AndroidLibraryComposeConvention"
        }
        register("androidApplication") {
            id = "partymania.android.application"
            implementationClass = "AndroidApplicationConvention"
        }
        register("androidApplicationCompose") {
            id = "partymania.android.application.compose"
            implementationClass = "AndroidApplicationComposeConvention"
        }
        register("androidApplicationHilt") {
            id = "partymania.android.application.hilt"
            implementationClass = "AndroidApplicationHiltConvention"
        }
        register("androidLibraryHilt") {
            id = "partymania.android.library.hilt"
            implementationClass = "AndroidLibraryHiltConvention"
        }
    }
}

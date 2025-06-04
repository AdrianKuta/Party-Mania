package dev.adriankuta.partymania

import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.plugins.JacocoTaskExtension
import org.gradle.testing.jacoco.tasks.JacocoReport
import java.util.Locale

// Mostly robbed off: https://github.com/android/nowinandroid/blob/main/build-logic/convention/src/main/kotlin/com/google/samples/apps/nowinandroid/Jacoco.kt
// ./gradlew jacocoTestReport will run lint, all the tests and generate the test coverage.
internal fun Project.configureJacoco(
    androidComponentsExtension: AndroidComponentsExtension<*, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    configure<JacocoPluginExtension> {
        toolVersion = libs.findVersion("jacoco").get().toString()
    }

    val jacocoTestReport = tasks.create("jacocoTestReport") {
        group = "Verification"
        description = "Create test coverage reports"
    }

    androidComponentsExtension.onVariants { variant ->
        if (variant.name.contains("DevDebug", ignoreCase = true)) {
            val unitTestTaskName = "test${variant.name.capitalize()}UnitTest"
//            val instrumentedTaskName = "defaultGroup${variant.name.capitalize()}AndroidTest" // This does dick on AGP version ancient (Weavr)
            val instrumentedTaskName = "connected${variant.name.capitalize()}AndroidTest"
            val lintTaskName = "lint${variant.name.capitalize()}"
            val detektTaskName = "detekt${variant.name.capitalize()}"

            val buildDir = layout.buildDirectory.get().asFile
            val reportTask =
                tasks.register(
                    "jacoco${unitTestTaskName.capitalize()}Report",
                    JacocoReport::class,
                ) {
                    dependsOn(unitTestTaskName)

                    // Dont bother with teh instrumented test if there isnt any.
                    if (file("${projectDir}/src/androidTest").walk().any { it.isFile }) {
                        dependsOn(instrumentedTaskName)
                    }

                    dependsOn(lintTaskName)
                    dependsOn(detektTaskName)

                    reports {
                        xml.required.set(true)
                        html.required.set(true)
                    }

                    classDirectories.setFrom(
                        fileTree("$buildDir/tmp/kotlin-classes/${variant.name}") {
                            exclude(coverageExclusions)
                        },
                    )

                    sourceDirectories.setFrom(
                        files(
                            "$projectDir/src/main/java",
                            "$projectDir/src/main/kotlin",
                        ),
                    )
                    executionData.setFrom(
                        fileTree("$buildDir") {
                            include("outputs/unit_test_code_coverage/devDebugUnitTest/*.exec")
                            include("outputs/managed_device_code_coverage/debug/flavors/dev/**/*.ec")
                        },
                    )
                }

            jacocoTestReport.dependsOn(reportTask)
        }
    }

    tasks.withType<Test>().configureEach {
        configure<JacocoTaskExtension> {
            // Required for JaCoCo + Robolectric
            // https://github.com/robolectric/robolectric/issues/2230
            isIncludeNoLocationClasses = true

            // Required for JDK 11 with the above
            // https://github.com/gradle/gradle/issues/5184#issuecomment-391982009
            excludes = listOf("jdk.internal.*")
        }
    }
}

private val coverageExclusions = listOf(
    // data binding
    "android/databinding/**/*.class",
    "**/android/databinding/*Binding.class",
    "**/android/databinding/*",
    "**/androidx/databinding/*",
    "**/BR.*",
    // android
    "**/R.class",
    "**/R$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*",
    // butterKnife
    "**/*\$ViewInjector*.*",
    "**/*\$ViewBinder*.*",
    // dagger
    "**/*_MembersInjector.class",
    "**/Dagger*Component.class",
    "**/Dagger*Component\$Builder.class",
    "**/*Module_*Factory.class",
    "**/di/module/*",
    "**/*_Factory*.*",
    "**/*Module*.*",
    "**/*Dagger*.*",
    "**/*Hilt*.*",
    // kotlin
    "**/*MapperImpl*.*",
    "**/*\$ViewInjector*.*",
    "**/*\$ViewBinder*.*",
    "**/BuildConfig.*",
    "**/*Component*.*",
    "**/*BR*.*",
    "**/Manifest*.*",
    "**/*\$Lambda$*.*",
    "**/*Companion*.*",
    "**/*Module*.*",
    "**/*Dagger*.*",
    "**/*Hilt*.*",
    "**/*MembersInjector*.*",
    "**/*_MembersInjector.class",
    "**/*_Factory*.*",
    "**/*_Provide*Factory*.*",
    "**/*Extensions*.*",
    "**/*JsonAdapter.*",
)

private fun String.capitalize() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

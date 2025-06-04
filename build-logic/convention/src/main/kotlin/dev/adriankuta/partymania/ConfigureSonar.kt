package dev.adriankuta.partymania

import org.gradle.api.Project

// If have got Sonar running locally you can try it by setting up a project and then running this
// script:
//
// #!/bin/bash -e
//
//./gradlew clean
//./gradlew jacocoTestReport
//./gradlew sonar \
//  -Dsonar.projectKey=YourProjectKey \
//  -Dsonar.projectName='YourProjectName' \
//  -Dsonar.host.url=http://localhost:9000 \
//  -Dsonar.token=sqp_sonarqubelkeywhateveritis
//
internal fun Project.configureSonar() {
//    configure<SonarExtension> {
//        properties {
//            val buildDir = layout.buildDirectory.get().toString()
//            property("sonar.androidLint.reportPaths", "$buildDir/reports/lint-results-devDebug.xml")
//            property("sonar.core.codeCoveragePlugin", "jacoco")
//            property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/**/*.xml")
//            property("sonar.gradle.skipCompile", true)
//            property("sonar.kotlin.detekt.reportPaths", "$buildDir/reports/detekt/devDebug.xml")
//            property("sonar.verbose", true)
//
//            property("sonar.junit.reportPaths",
//                "$buildDir/test-results/testDevDebugUnitTest,$buildDir/outputs/androidTest-results/managedDevice/debug/flavors/dev/pixel2api30")
//        }
//    }
}

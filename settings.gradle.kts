pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    // If the issue below gets fixed then the repositories blocks can come out the project modules and this can go back
    // to being RepositoriesMode.FAIL_ON_PROJECT_REPOS
    // https://github.com/GoogleCloudPlatform/artifact-registry-maven-tools/issues/71
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

rootProject.name = "PartyMania"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:designsystem")
include(":core:ui")
include(":core:util")
include(":domain:types")
include(":domain:yesorno")
include(":feature:game:questions")
include(":model:data:room")
include(":model:data:simple")
include(":model:datasource:characters")
include(":model:repository")
include(":ui:home")
include(":ui:sharedui")

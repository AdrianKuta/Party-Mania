@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.data"
}

dependencies {
    api(projects.core.model)
    api(projects.core.common)

    implementation(projects.core.util)
    implementation(libs.gson)
    implementation(libs.timber)
}
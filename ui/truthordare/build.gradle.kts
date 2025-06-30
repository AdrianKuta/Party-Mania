plugins {
    alias(libs.plugins.partymania.android.library.compose)
    alias(libs.plugins.partymania.android.library.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.adriankuta.partymania.ui.truthordare"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.util)
    implementation(projects.domain.truthordare)
    implementation(projects.ui.sharedui)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.timber)
}
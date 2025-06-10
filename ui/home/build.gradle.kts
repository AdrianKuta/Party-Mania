plugins {
    alias(libs.plugins.partymania.android.library.compose)
    alias(libs.plugins.partymania.android.library.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.adriankuta.partymania.ui.home"
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.timber)
}
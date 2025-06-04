plugins {
    alias(libs.plugins.partymania.android.library.compose)
    alias(libs.plugins.partymania.android.library.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.ui.sharedui"
}

dependencies {
    implementation(libs.timber)
}
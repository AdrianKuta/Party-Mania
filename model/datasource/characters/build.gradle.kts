plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.model.datasource.characters"
}

dependencies {
    implementation(libs.timber)
}
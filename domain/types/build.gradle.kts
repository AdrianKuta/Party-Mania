plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.android.library.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.domain.types"
}

dependencies {
    implementation(projects.core.util)
    implementation(libs.timber)
}
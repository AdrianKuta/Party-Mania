plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.model.data.room"
}

dependencies {
    implementation(projects.domain.yesorno)
    implementation(libs.timber)
}
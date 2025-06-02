plugins {
    alias(libs.plugins.partymania.android.library)
}

android {
    namespace = "dev.adriankuta.partymania.domain.yesornow"
}

dependencies {
    api(projects.domain.types)
    implementation(libs.timber)
}
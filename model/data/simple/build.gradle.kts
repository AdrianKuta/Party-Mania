plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.android.library.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.model.data.simple"
}

dependencies {
    implementation(projects.model.datasource.characters)
    implementation(projects.core.util)

    implementation(libs.timber)
    implementation(libs.gson)
}

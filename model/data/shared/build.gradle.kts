plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.android.library.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.model.data.shared"
}

dependencies {
    implementation(projects.model.datasource.shared)
}

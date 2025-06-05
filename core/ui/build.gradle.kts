plugins {
    alias(libs.plugins.partymania.android.library.compose)
}

android {
    namespace = "dev.adriankuta.partymania.core.ui"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.domain.types)

    // Compose
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.graphics)
}
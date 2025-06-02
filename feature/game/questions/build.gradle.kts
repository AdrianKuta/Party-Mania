plugins {
    alias(libs.plugins.partymania.android.feature)
    alias(libs.plugins.partymania.android.library.compose)
}

android {
    namespace = "dev.adriankuta.partymania.feature.game.questions"
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain.types)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
}
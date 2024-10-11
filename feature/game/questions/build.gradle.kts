@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.partymania.android.feature)
    alias(libs.plugins.partymania.android.library.compose)
}

android {
    namespace = "dev.adriankuta.partymania.feature.game.questions"
}

dependencies {
    implementation(projects.core.ui)
    implementation(projects.data)

    // Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)
}
@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.compose)
}

android {
    namespace = "dev.adriankuta.partymania.feature.game.questions"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":data"))

    implementation(libs.androidx.lifecycle.runtimeCompose)

    // Compose
    implementation(libs.androidx.compose.compiler)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.material3)

    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.introShowcaseView)
}
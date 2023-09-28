@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.compose)
}

android {
    namespace = "dev.adriankuta.partymania.core.ui"
}

dependencies {

    // Compose
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.graphics)
}
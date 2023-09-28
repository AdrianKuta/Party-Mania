@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.partymania.android.library)
}

android {
    namespace = "dev.adriankuta.partymania.data"
}

dependencies {
    implementation(project(":core:util"))
    implementation(libs.gson)
    implementation(libs.timber)
}
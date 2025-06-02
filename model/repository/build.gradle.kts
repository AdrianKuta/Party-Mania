plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.model.repository"
}

dependencies {
    implementation(projects.domain.yesorno)
    implementation(projects.model.datasource.characters)

    implementation(libs.timber)

    testImplementation("io.mockk:mockk:1.13.8")
}

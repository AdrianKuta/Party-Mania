plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.android.library.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.model.repository"
}

dependencies {
    implementation(projects.domain.gametypes)
    implementation(projects.domain.truthordare)
    implementation(projects.domain.yesorno)
    implementation(projects.model.datasource.characters)
    implementation(projects.model.datasource.shared)
    implementation(projects.model.datasource.questions)

    implementation(libs.timber)
}

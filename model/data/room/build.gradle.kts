plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.android.library.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "dev.adriankuta.partymania.model.data.room"
}

dependencies {
    implementation(projects.model.datasource.shared)
    implementation(projects.model.datasource.questions)

    // Room dependencies
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Gson for JSON serialization/deserialization
    implementation(libs.gson)
}

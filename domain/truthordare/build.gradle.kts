plugins {
    alias(libs.plugins.partymania.android.library)
}

android {
    namespace = "dev.adriankuta.partymania.domain.truthordare"
}

dependencies {
    api(projects.domain.types)
}
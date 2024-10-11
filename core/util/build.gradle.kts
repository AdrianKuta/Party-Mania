@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.partymania.android.library)
    alias(libs.plugins.partymania.hilt)
}

android {
    namespace = "dev.adriankuta.partymania.core.util"
}

dependencies {

}
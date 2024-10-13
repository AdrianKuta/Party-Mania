import dev.adriankuta.partymania.PartyManiaBuildType

@Suppress("DSL_SCOPE_VIOLATION") // Remove when fixed https://youtrack.jetbrains.com/issue/KTIJ-19369
plugins {
    alias(libs.plugins.partymania.android.application)
    alias(libs.plugins.partymania.android.application.compose)
    alias(libs.plugins.partymania.android.application.flavors)
    alias(libs.plugins.partymania.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.adriankuta.partymania"

    defaultConfig {
        applicationId = "dev.adriankuta.partymania"
        versionCode = 7
        versionName = "0.0.1"
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        debug {
            applicationIdSuffix = PartyManiaBuildType.DEBUG.applicationIdSuffix
        }
        release {
            applicationIdSuffix = PartyManiaBuildType.RELEASE.applicationIdSuffix
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(projects.core.designsystem)
    implementation(projects.feature.game.questions)

    implementation(libs.timber)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)

    // Architecture components
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit4)

    androidTestImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit)
}
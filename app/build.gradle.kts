import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.partymania.android.application.compose)
    alias(libs.plugins.partymania.android.application.hilt)
}

android {
    namespace = "dev.adriankuta.partymania"

    defaultConfig {
        applicationId = "dev.adriankuta.partymania"
        versionCode = 10
        versionName = "0.0.1-${versionCode}"
        //signingConfig = signingConfigs.getByName("debug")
    }

    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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

    implementation(projects.domain.gametypes)
    implementation(projects.domain.yesorno)

    implementation(projects.model.repository)
    implementation(projects.model.data.room)
    implementation(projects.model.data.simple)

    implementation(projects.ui.home)
    implementation(projects.ui.truthordare)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.app.update.ktx)
}
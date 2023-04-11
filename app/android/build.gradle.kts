plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "com.joshrose"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":app:common"))
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(project(":plots-for-compose"))
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.joshrose.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }
}
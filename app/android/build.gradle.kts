plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "com.joshrose"
version = "1.0-SNAPSHOT"

dependencies {
    api(project(":app:common"))
    api(project(":plots-for-compose"))
    api(deps.androidx.activity.activityCompose)
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
}
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
    namespace = "com.joshrose.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.joshrose.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
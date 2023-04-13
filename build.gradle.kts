@file:Suppress("DSL_SCOPE_VIOLATION")

group = "com.joshrose"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    alias(deps.plugins.kotlin.jvm) apply false
    alias(deps.plugins.kotlin.multiplatform) apply false
    alias(deps.plugins.kotlin.android) apply false
    alias(deps.plugins.android.application) apply false
    alias(deps.plugins.android.library) apply false
    alias(deps.plugins.jetbrains.compose) apply false
}
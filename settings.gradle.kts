pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        kotlin("android").version(extra["kotlin.version"] as String)
        id("com.android.application").version(extra["agp.version"] as String)
        id("com.android.library").version(extra["agp.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
        id("org.jetbrains.kotlin.jvm") version "1.8.0"
        id("com.android.library") version "7.4.1"
        id("org.jetbrains.kotlin.android") version "1.8.0"
    }
}

rootProject.name = "PlotsForCompose"

include(":android", ":desktop", ":common", ":PlotsForCompose-core")

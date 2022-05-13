pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "template-kit"
include(":androidApp")
include(":shared")
enableFeaturePreview("VERSION_CATALOGS")

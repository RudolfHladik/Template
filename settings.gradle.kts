pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "Template"
include(":androidApp")
include(":shared")
enableFeaturePreview("VERSION_CATALOGS")

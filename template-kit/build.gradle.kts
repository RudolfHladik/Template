buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs") as org.gradle.accessors.dm.LibrariesForLibs

        classpath(libs.gradlePlugin.kotlin)
        classpath(libs.gradlePlugin.android)
        classpath(kotlin("serialization", version = libs.versions.kotlin.get()))
        classpath(libs.gradlePlugin.sqldelight)

    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

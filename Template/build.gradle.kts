buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs") as org.gradle.accessors.dm.LibrariesForLibs

        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.android.gradlePlugin)
        classpath(kotlin("serialization", version = libs.versions.kotlin.get()))
        classpath(libs.sqldelight.plugin)

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

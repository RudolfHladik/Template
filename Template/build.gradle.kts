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
        gradlePluginPortal()
        google()
        mavenCentral()

        maven {
            setUrl("https://oss.sonatype.org/content/repositories/snapshots/")
        }

        maven {
            // KTOR MM
            setUrl("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
//    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.squareup.sqldelight")
//    id("com.apollographql.apollo3").version("3.0.0-dev14")
}

version = "1.0"
sqldelight {
    database("CommonDatabase") {
        packageName = "com.rudolfhladik.kmm.template.db"
    }
}

kotlin {
    android()

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            xcf.add(this)
        }
    }

//    cocoapods {
//        summary = "Some description for the Shared Module"
//        homepage = "Link to the Shared Module homepage"
//        ios.deploymentTarget = "14.1"
//        frameworkName = "shared"
//        podfile = project.file("../iosApp/Podfile")
//    }

    sourceSets {
        val libs = project.extensions.getByType<VersionCatalogsExtension>()
            .named("libs") as org.gradle.accessors.dm.LibrariesForLibs

        val commonMain by getting {
            dependencies {
                // Coroutines
                implementation(libs.kotlinx.coroutines)
                // Serialization
                implementation(libs.kotlinx.serialization)
                implementation(libs.kotlinx.serializationJson)
                // Ktor
                implementation(libs.ktor.client.core)
                // SqlDelight
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.ext)
                // Apollo
//                implementation("com.apollographql.apollo3:apollo-runtime:3.0.0-dev14")

                // Arkitekt
                api("app.futured.arkitekt:km-usecases:0.2.4-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                // Ktor android engine
                implementation(libs.ktor.client.android)
                // SqlDelight android driver
                implementation(libs.sqldelight.android)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
//        val iosMain by getting {
//            dependencies {
//                // Ktor ios engine
//                implementation(libs.ktor.client.ios)
//                // SqlDelight iOS driver
//                implementation(libs.sqldelight.ios)
//                // Arkitekt viewmodel
////                api("app.futured.arkitekt:km-viewmodel:0.1.2-SNAPSHOT")
//            }
//        }
//        val iosTest by getting

        ////
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                // Ktor ios engine
                implementation(libs.ktor.client.ios)
                // SqlDelight iOS driver
                implementation(libs.sqldelight.ios)
                // Arkitekt viewmodel
//                api("app.futured.arkitekt:km-viewmodel:0.1.2-SNAPSHOT")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

android {
    compileSdk = 30
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 30
    }
}

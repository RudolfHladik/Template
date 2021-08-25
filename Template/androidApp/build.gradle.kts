plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 30
    defaultConfig {
        applicationId = "com.rudolfhladik.kmm.template.android"
        minSdk = 26
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
    }
    val libs = project.extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs") as org.gradle.accessors.dm.LibrariesForLibs

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.androidx.compose)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycleKtx)
}

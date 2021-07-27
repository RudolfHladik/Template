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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
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

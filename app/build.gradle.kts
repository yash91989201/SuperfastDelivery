import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.superfastdelivery"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.superfastdelivery"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localProperties = gradleLocalProperties(rootDir, providers)
        val googleMapsApiKey = localProperties.getProperty("GOOGLE_MAPS_API_KEY") ?: ""

        manifestPlaceholders["GOOGLE_MAPS_API_KEY"] = googleMapsApiKey
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core Modules - Core components for app architecture and utilities
    implementation(projects.core.appState)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.core.di)

    // Auth Feature - Manages authentication-related functionality
    implementation(projects.feature.auth.data)
    implementation(projects.feature.auth.domain)
    implementation(projects.feature.auth.ui)

    // Account Feature - Handles user account and profile management
    implementation(projects.feature.account.data)
    implementation(projects.feature.account.domain)
    implementation(projects.feature.account.ui)

    // Search Feature - Provides search functionality across the app
    implementation(projects.feature.search.data)
    implementation(projects.feature.search.domain)
    implementation(projects.feature.search.ui)

    // Restaurant Feature - Manages restaurant-related features and data
    implementation(projects.feature.restaurant.data)
    implementation(projects.feature.restaurant.domain)
    implementation(projects.feature.restaurant.ui)

    // Splash Screen - AndroidX SplashScreen API for a smooth launch experience
    implementation(libs.androidx.core.splashscreen)

    // Dependency Injection (Hilt) - Provides Hilt for dependency injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Navigation - Manages in-app navigation using Jetpack Compose Navigation
    implementation(libs.navigation.compose)

    // Serialization - JSON serialization/deserialization support
    implementation(libs.kotlinx.serialization.json)

    // AndroidX Core - Core libraries and Compose UI for modern Android development
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Unit Testing - JUnit for unit testing
    testImplementation(libs.junit)

    // Instrumentation Testing - Libraries for Android UI and instrumentation testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug Tools - Compose UI debugging tools for development
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
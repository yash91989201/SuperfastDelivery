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
    // modules
    implementation(projects.common)
    implementation(projects.core.navigation)
    // auth feature
    implementation(projects.feature.auth.data)
    implementation(projects.feature.auth.domain)
    implementation(projects.feature.auth.ui)
    // account feature
    implementation(projects.feature.account.data)
    implementation(projects.feature.account.domain)
    implementation(projects.feature.account.ui)
    // search feature
    implementation(projects.feature.search.data)
    implementation(projects.feature.search.domain)
    implementation(projects.feature.search.ui)
    // restaurant feature
    implementation(projects.feature.restaurant.data)
    implementation(projects.feature.restaurant.domain)
    implementation(projects.feature.restaurant.ui)
    // splash screen
    implementation(libs.androidx.core.splashscreen)
    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // navigation
    implementation(libs.navigation.compose)
    // serialization
    implementation(libs.kotlinx.serialization.json)
    //
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
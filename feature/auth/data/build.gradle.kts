plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.example.auth.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    // Modules - Core components and feature-specific modules
    implementation(projects.core.appState)
    implementation(projects.schema)
    implementation(projects.feature.auth.domain)

    // Dependency Injection - Dagger Hilt for DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Apollo - GraphQL client for network operations
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.adapters.core)

    // Networking - Logging interceptor for HTTP requests
    implementation(libs.logging.interceptor)

    // Data Storage - Preferences and ProtoBuf support
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.protobuf.javalite)

    // AndroidX Core Libraries - Essential Android components
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Unit Testing
    testImplementation(libs.junit)

    // Instrumentation Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

apollo {
    // Service names must match
    service("service") {
        packageName.set("com.example.auth.data")
        dependsOn(projects.schema)
    }
}
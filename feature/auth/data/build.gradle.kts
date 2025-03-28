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
    // modules
    implementation(projects.core.appState)
    implementation(projects.schema)
    implementation(projects.feature.auth.domain)
    // dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // apollo
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.adapters.core)
    // logging interceptor
    implementation(libs.logging.interceptor)
    // datastore preferences
    implementation(libs.androidx.datastore.preferences)
    // protobuf javalite
    implementation(libs.protobuf.javalite)
    //
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
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
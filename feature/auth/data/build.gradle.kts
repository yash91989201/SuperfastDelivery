plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.example.auth.data"
    compileSdk = 34

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
    implementation(project(":schema"))
    implementation(project(":feature:auth:domain"))
    // dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    // apollo
    implementation(libs.apollo.runtime)
    // logging interceptor
    implementation(libs.logging.interceptor)
//    implementation(libs.apollo.adapters.core)
//    implementation(libs.apollo.adapters.kotlinx.datetime)
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

        dependsOn(project(":schema"))
    }
}
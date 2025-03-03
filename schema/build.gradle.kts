plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.example.schema"
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
    // apollo
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.adapters.core)
    //
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

apollo {
    service("service") {
        packageName.set("com.example.schema")
        schemaFile.set(file("src/main/graphql/schema.graphqls"))
        mapScalar("Date","java.time.LocalDate","com.apollographql.adapter.core.JavaLocalDateAdapter")
        generateKotlinModels.set(true)
        generateApolloMetadata.set(true)

        introspection {
            endpointUrl.set("http://172.25.160.1:8081/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}
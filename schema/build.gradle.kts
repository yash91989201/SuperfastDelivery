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
    // Apollo - GraphQL client
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.adapters.core)

    // AndroidX Core Libraries
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
    service("service") {
        packageName.set("com.example.schema")
        schemaFile.set(file("src/main/graphql/schema.graphqls"))

        generateKotlinModels.set(true)
        generateApolloMetadata.set(true)

        mapScalar(
            "Date",
            "java.time.LocalDate",
            "com.apollographql.adapter.core.JavaLocalDateAdapter"
        )

        mapScalar(
            "DateTime",
            "java.time.Instant",
            "com.apollographql.adapter.core.JavaInstantAdapter"
        )

        mapScalar(
            "Time",
            "java.time.LocalTime",
            "com.apollographql.adapter.core.JavaLocalTimeAdapter"
        )

        introspection {
            endpointUrl.set("http://192.168.1.6:8081/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}
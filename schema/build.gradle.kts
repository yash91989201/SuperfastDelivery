plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.apollo)
}

android {
    namespace = "com.example.schema"
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
    // apollo
    implementation(libs.apollo.runtime)
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
    service("service") {
        packageName.set("com.example.schema")
        schemaFile.set(file("src/main/graphql/schema.graphqls"))
        generateKotlinModels.set(true)
        generateApolloMetadata.set(true)
//        mapScalar("DateTime", "com.apollographql.adapter.datetime.KotlinxLocalTimeAdapter")
//        mapScalar("Date", "com.apollographql.adapter.datetime.KotlinxLocalDateAdapter")

        introspection {
            endpointUrl.set("http://54.87.20.16:8081/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}
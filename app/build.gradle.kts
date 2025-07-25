plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.example.shescreen"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shescreen"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

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
    //
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.androidx.foundation)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.database)
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.material.icons.extended.android)
    // Network Image
    implementation(libs.coil.compose)

    // Supabase
    implementation(platform(libs.supabase.bom))
    implementation(libs.realtime.kt)
    implementation(libs.postgrest.kt)
    implementation(libs.ktor.client.android)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.converter.gson.v300)
    implementation(libs.logging.interceptor)

    //Work manager
    implementation("androidx.work:work-runtime-ktx:2.10.2")

    // PDF generation
    implementation("com.itextpdf:itextpdf:5.5.13.4")
    implementation("com.itextpdf:itext-pdfa:5.5.13.4")

    // For better date formatting
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
}
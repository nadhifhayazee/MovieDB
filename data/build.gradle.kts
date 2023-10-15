@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.gradle)
    id("kotlin-kapt")

}

android {
    namespace = "com.nadhifhayazee.moviedb.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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

            val apiKey = "da422ee3656cd47af7d9d2a50ec0e959"

            buildConfigField("String", "API_KEY", "\"" + apiKey + "\"")
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "IMAGE_URL", "\"" + "https://image.tmdb.org/t/p/w185" + "\"")
            buildConfigField("String", "BACKDROP_URL", "\"" + "https://image.tmdb.org/t/p/w500" + "\"")
        }

        debug {
            val apiKey = "da422ee3656cd47af7d9d2a50ec0e959"

            buildConfigField("String", "API_KEY", "\"" + apiKey + "\"")
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "IMAGE_URL", "\"" + "https://image.tmdb.org/t/p/w185" + "\"")
            buildConfigField("String", "BACKDROP_URL", "\"" + "https://image.tmdb.org/t/p/w500" + "\"")

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
        buildConfig = true
    }
}

dependencies {

    implementation(project(":commons"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.bundles.retrofit)

    implementation(libs.bundles.paging)

    debugImplementation(libs.chucker.debuger)

}
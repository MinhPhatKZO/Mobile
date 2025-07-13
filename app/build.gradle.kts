import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    // ❌ XÓA dòng này vì bạn dùng Java
    // id("kotlin-kapt")
}

// Đọc key từ file keys.properties
val keyProps = Properties()
val keyFile = rootProject.file("keys.properties")
if (keyFile.exists()) {
    keyProps.load(keyFile.inputStream())
}
val openAiKey = keyProps.getProperty("OPENAI_API_KEY") ?: "sk-PLACEHOLDER"

android {
    namespace = "com.example.projecttng"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projecttng"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "OPENAI_API_KEY", "\"$openAiKey\"")
    }
    buildFeatures {
        buildConfig = true
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
}

dependencies {
    implementation(libs.okhttp)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Glide cho Java
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    implementation(
        fileTree(
            mapOf(
                "dir" to "C:\\Users\\MINH PHAT\\Downloads\\zalopay",
                "include" to listOf("*.aar", "*.jar"),
                "exclude" to listOf<String>()
            )
        )
    )

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

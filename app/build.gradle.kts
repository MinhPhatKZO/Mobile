import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    // ❌ Không cần kotlin-kapt nếu dùng Java
}

// ✅ Đọc key sau khi khởi tạo biến
val geminiKey: String by lazy {
    val props = Properties()
    val keyFile = rootProject.file("keys.properties")
    if (keyFile.exists()) {
        props.load(keyFile.inputStream())
        val key = props.getProperty("GEMINI_API_KEY") ?: "AIza-PLACEHOLDER"
        println("🔐 Gemini API key loaded from file: $key") // ✅ In ra sau khi có key
        key
    } else {
        println("⚠️ File keys.properties không tồn tại")
        "AIza-PLACEHOLDER"
    }
}

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

        // ✅ Đưa key vào BuildConfig
        buildConfigField("String", "GEMINI_API_KEY", "\"$geminiKey\"")
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

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.worldtech.awesomeandroidchart"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.worldtech.awesomeandroidchart"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {

//    implementation("androidx.core:core-ktx:1.9.0")
    api("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    api("io.github.scwang90:refresh-layout-kernel:2.0.6")      //核心必须依赖
    api("io.github.scwang90:refresh-header-classics:2.0.6")     //经典刷新头
    api("io.github.scwang90:refresh-footer-classics:2.0.6")     //经典加载

    api("com.blankj:utilcodex:1.31.1")
    api("io.github.cymchad:BaseRecyclerViewAdapterHelper:3.0.14")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    api("androidx.annotation:annotation:1.6.0")
    api("androidx.media:media:1.6.0")
    api("androidx.recyclerview:recyclerview:1.3.0")
    api("com.google.android.flexbox:flexbox:3.0.0") //标签布局+RecyclerView


}
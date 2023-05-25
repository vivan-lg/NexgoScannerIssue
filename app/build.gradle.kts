plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.acme.nexgo.scannerissue"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.acme.nexgo.scannerissue"
        minSdk = 25
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        ndk {
            abiFilters.add("armeabi-v7a")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("com.github.ajohnson388:DLParser-Kotlin:1.0.0")

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar"))))
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}

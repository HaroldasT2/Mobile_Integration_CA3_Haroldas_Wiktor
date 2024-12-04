plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.homeguard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.homeguard"
        minSdk = 24
        targetSdk = 34
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

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kapt {
        javacOptions {
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED")
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED")
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED")
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED")
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED")
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED")
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED")
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED")
            option("--add-opens", "jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.3.0-alpha01")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation(libs.androidx.foundation.android)
    testImplementation("junit:junit:4.14.0")
    androidTestImplementation("androidx.test.ext:junit:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.room:room-runtime:2.6.0")
    kapt("androidx.room:room-compiler:2.6.0")
    kapt("androidx.lifecycle:lifecycle-compiler:2.7.0")
    implementation("androidx.room:room-ktx:2.6.0")
    implementation("io.coil-kt:coil-compose:2.1.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
}
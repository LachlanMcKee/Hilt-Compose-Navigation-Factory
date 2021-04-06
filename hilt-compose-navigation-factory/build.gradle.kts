plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.kapt")
  id("com.vanniktech.maven.publish")
}

android {
  compileSdkVersion(30)

  defaultConfig {
    minSdkVersion(21)
    targetSdkVersion(30)
  }

  buildFeatures {
    compose = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.0.0-beta04"
  }
}

dependencies {
  implementation("androidx.navigation:navigation-compose:1.0.0-alpha10")
  implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha01")
  kapt("com.google.dagger:dagger-compiler:2.34")
  kapt("com.google.dagger:hilt-compiler:2.34-beta")
}

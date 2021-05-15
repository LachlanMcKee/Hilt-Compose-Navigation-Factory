plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.kapt")
  id("dagger.hilt.android.plugin")
}

android {
  compileSdkVersion(30)

  defaultConfig {
    minSdkVersion(21)
    targetSdkVersion(30)

    applicationId = "net.lachlanmckee.jetpack.navigation.hilt"
    versionCode = System.getenv("BITRISE_BUILD_NUMBER")?.toIntOrNull() ?: 1
    versionName = "0.0.1"

    testOptions {
      execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments["clearPackageData"] = "true"
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.0.0-beta04"
  }

  buildTypes {
    getByName("debug") {
      applicationIdSuffix = ".debug"
      signingConfig = signingConfigs.getByName("debug")
    }
    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  packagingOptions {
    resources {
      excludes += "META-INF/AL2.0"
      excludes += "META-INF/LGPL2.1"
    }
  }
}

dependencies {
  implementation(project(":hilt-compose-navigation-factory"))
  implementation(project(":sample:features:feature1"))
  implementation(project(":sample:features:feature2"))

  implementation("androidx.appcompat:appcompat:1.3.0-rc01")
  implementation("androidx.activity:activity-compose:1.3.0-alpha06")

  implementation("androidx.compose.ui:ui:1.0.0-beta04")
  implementation("androidx.compose.foundation:foundation:1.0.0-beta04")
  implementation("androidx.compose.material:material:1.0.0-beta06")

  // Dagger
  implementation("com.google.dagger:dagger:2.35.1")
  implementation("com.google.dagger:hilt-android:2.35.1")
  kapt("com.google.dagger:dagger-compiler:2.35.1")
  kapt("com.google.dagger:hilt-compiler:2.35.1")
  implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha01")

  androidTestUtil("androidx.test:orchestrator:1.3.0")
  androidTestImplementation("androidx.test.ext:junit:1.1.2")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
  androidTestImplementation("androidx.test:runner:1.3.0")
  androidTestImplementation("androidx.test:rules:1.3.0")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.0.0-beta06")
}

plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.kapt")
  id("dagger.hilt.android.plugin")
  id("io.gitlab.arturbosch.detekt")
}

detekt {
  config = files("$rootDir/detekt.yml")
}

android {
  namespace = "net.lachlanmckee.hilt.compose.navigation.factory.app"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()

    applicationId = "net.lachlanmckee.jetpack.navigation.hilt"
    versionCode = 1
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

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
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
  implementation(projects.library)
  implementation(projects.sample.features.feature1)
  implementation(projects.sample.features.feature2)

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.activityCompose)

  implementation(libs.compose.ui)
  implementation(libs.compose.foundation)
  implementation(libs.compose.material)

  // Dagger
  implementation(libs.bundles.daggerRuntimes)
  kapt(libs.bundles.daggerCompilers)
  implementation(libs.dagger.hilt.navigationCompose)

  androidTestUtil(libs.espresso.orchestrator)
  androidTestImplementation(libs.bundles.espressoCore)
  androidTestImplementation(libs.compose.testing)
}

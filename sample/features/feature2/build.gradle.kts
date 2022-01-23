plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.kapt")
}

android {
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
  }

  buildFeatures {
    compose = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  composeOptions {
    kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
  }
}

dependencies {
  implementation(projects.library)
  implementation(libs.compose.ui)
  implementation(libs.compose.foundation)
  implementation(libs.compose.material)
  implementation(libs.compose.runtime.livedata)
  implementation(libs.androidx.lifecycle.livedata)
  implementation(libs.compose.navigation)
  implementation(libs.dagger.hilt.navigationCompose)

  // Dagger
  implementation(libs.bundles.daggerRuntimes)
  kapt(libs.bundles.daggerCompilers)
  kapt(projects.compiler)
}

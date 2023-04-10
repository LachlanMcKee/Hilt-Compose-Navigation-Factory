plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.kapt")
  id("io.gitlab.arturbosch.detekt")
}

detekt {
  config = files("$rootDir/detekt.yml")
}

android {
  namespace = "net.lachlanmckee.hilt.compose.navigation.factory.feature1"
  compileSdk = libs.versions.compileSdk.get().toInt()

  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
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
}

dependencies {
  api(projects.library)
  implementation(libs.compose.ui)
  implementation(libs.compose.foundation)
  implementation(libs.compose.material)
  implementation(libs.compose.runtime.livedata)
  implementation(libs.androidx.lifecycle.livedata)
  implementation(libs.compose.navigation)
  implementation(libs.dagger.hilt.navigationCompose)

  // Dagger
  api(libs.dagger.runtime)
  implementation(libs.dagger.hilt.runtime)
  kapt(libs.bundles.daggerCompilers)
  kapt(projects.compiler)
}

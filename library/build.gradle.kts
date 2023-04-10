plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.kapt")
  id("com.vanniktech.maven.publish")
  id("io.gitlab.arturbosch.detekt")
}

detekt {
  config = files("$rootDir/detekt.yml")
}

android {
  namespace = "net.lachlanmckee.hilt.compose.navigation.factory"
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
  implementation(libs.dagger.hilt.navigationCompose)
  kapt(libs.bundles.daggerCompilers)
}

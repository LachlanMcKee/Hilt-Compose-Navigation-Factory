import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    google()
    jcenter()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:7.0.0-alpha15")
    classpath(kotlin("gradle-plugin", version = "1.4.32"))
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.35.1")
    classpath("com.vanniktech:gradle-maven-publish-plugin:0.14.2")
    classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.32")
  }
}

allprojects {
  repositories {
    google()
    jcenter()
  }
}

val rootDir = projectDir

subprojects {
  buildscript {
    repositories {
      google()
      jcenter()
    }
  }

  repositories {
    google()
    jcenter()
  }

  pluginManager.withPlugin("java") {
    configure<JavaPluginExtension> {
      sourceCompatibility = JavaVersion.VERSION_1_8
      targetCompatibility = JavaVersion.VERSION_1_8
    }
  }

  tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "1.8"
      useIR = true
      freeCompilerArgs = freeCompilerArgs + listOf(
        "-Xopt-in=kotlin.ExperimentalStdlibApi",
        "-Xopt-in=kotlin.RequiresOptIn",
        "-Xopt-in=kotlin.contracts.ExperimentalContracts",
        "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xopt-in=androidx.compose.foundation.ExperimentalFoundationApi"
      )
    }
  }

  configurations.all {
    resolutionStrategy {
      eachDependency {
        when (requested.module.toString()) {
          "androidx.compose.runtime:runtime" -> useVersion("1.0.0-beta06")
          "androidx.compose.ui:ui" -> useVersion("1.0.0-beta06")
        }
      }
    }
  }
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath(libs.plugin.androidTools)
    classpath(kotlin("gradle-plugin", version = libs.versions.kotlin.get()))
    classpath(libs.plugin.hiltAndroidGradle)
    classpath(libs.plugin.mavenPublish)
  }
}

plugins {
  alias(libs.plugins.spotless)
  alias(libs.plugins.detekt)
  id("com.autonomousapps.dependency-analysis") version libs.versions.dependencyAnalysis.get()
}

spotless {
  format("misc") {
    target("*.md", ".gitignore")
    trimTrailingWhitespace()
    indentWithSpaces(2)
    endWithNewline()
  }
  kotlin {
    ktlint().editorConfigOverride(
      mapOf(
        "indent_size" to "2",
        "disabled_rules" to "no-wildcard-imports",
        "ij_kotlin_allow_trailing_comma" to "false",
        "ij_kotlin_allow_trailing_comma_on_call_site" to "false"
      )
    )
    target("**/*.kt")
    trimTrailingWhitespace()
    endWithNewline()
    targetExclude("**/build/**", "**/GeneratedLibraries.kt")
  }
  kotlinGradle {
    ktlint().editorConfigOverride(
      mapOf(
        "indent_size" to "2",
        "ij_kotlin_allow_trailing_comma" to "false",
        "ij_kotlin_allow_trailing_comma_on_call_site" to "false"
      )
    )
    target("**/*.gradle.kts")
    trimTrailingWhitespace()
    endWithNewline()
    targetExclude("**/build/**")
  }
}

dependencyAnalysis {
  issues {
    all {
      onIncorrectConfiguration {
        severity("fail")
      }
      onUnusedDependencies {
        severity("fail")
      }
    }
    project(":sample:app") {
      onUnusedDependencies {
        severity("fail")

        // These are used by the hilt compiler plugin
        exclude(
          "androidx.hilt:hilt-navigation-compose",
          "com.google.dagger:dagger",
          ":sample:features:feature1",
          ":sample:features:feature2"
        )
      }
    }
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}

val rootDir = projectDir

subprojects {
  buildscript {
    repositories {
      google()
      mavenCentral()
    }
  }

  repositories {
    google()
    mavenCentral()
  }

  pluginManager.withPlugin("java") {
    configure<JavaPluginExtension> {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
    }
  }

  tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "17"
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
          "androidx.compose.runtime:runtime" -> useVersion(libs.versions.compose.get())
          "androidx.compose.ui:ui" -> useVersion(libs.versions.compose.get())
        }
      }
    }
  }
}

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    // TODO: Once gradle adds support for accessing libs within build script, this can be removed.
    val libs = project.extensions
      .getByType<VersionCatalogsExtension>()
      .named("libs") as org.gradle.accessors.dm.LibrariesForLibs

    classpath(libs.plugin.androidTools)
    classpath(kotlin("gradle-plugin", version = libs.versions.kotlin.get()))
    classpath(libs.plugin.hiltAndroidGradle)
    classpath(libs.plugin.hiltAndroidGradle)
    classpath(libs.plugin.mavenPublish)
    classpath(libs.plugin.dokka)
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
          "androidx.compose.runtime:runtime" -> useVersion(libs.versions.compose.get())
          "androidx.compose.ui:ui" -> useVersion(libs.versions.compose.get())
        }
      }
    }
  }
}

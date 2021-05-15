import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    google()
    jcenter()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:7.0.0-alpha14")
    classpath(kotlin("gradle-plugin", version = "1.4.32"))
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.34-beta")
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
}

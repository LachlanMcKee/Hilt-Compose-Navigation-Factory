plugins {
  id("java-library")
  id("kotlin")
  id("kotlin-kapt")
  id("com.vanniktech.maven.publish")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

@Suppress("AnnotationProcessorOnCompilePath")
dependencies {
  kapt(libs.autoService)
  implementation(libs.autoService)
  implementation(libs.dagger.hilt.compiler)
}

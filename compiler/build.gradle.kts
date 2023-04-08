plugins {
  id("java-library")
  id("kotlin")
  id("kotlin-kapt")
  id("com.vanniktech.maven.publish")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  implementation(libs.javapoet)
  implementation(libs.auto.common)
  compileOnly(libs.auto.service.annotations)
  kapt(libs.auto.service.compiler)
  compileOnly(libs.incap.core)
  kapt(libs.incap.compiler)
}

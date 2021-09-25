package net.lachlanmckee.hilt.compose.navigation.factory.compiler

import com.squareup.javapoet.ClassName

object ClassNames {
  private val FACTORY_PACKAGE = "net.lachlanmckee.hilt.compose.navigation.factory"
  val NAVIGATION_FACTORY_ANNOTATION = ClassName.get(FACTORY_PACKAGE, "HiltComposeNavigationFactory")
  val COMPOSE_NAVIGATION_FACTORY = ClassName.get(FACTORY_PACKAGE, "ComposeNavigationFactory")

  val BINDS = ClassName.get("dagger", "Binds")
  val INTO_SET = ClassName.get("dagger.multibindings", "IntoSet")
  val INSTALL_IN = ClassName.get("dagger.hilt", "InstallIn")
  val MODULE = ClassName.get("dagger", "Module")
  val ORIGINATING_ELEMENT = ClassName.get("dagger.hilt.codegen", "OriginatingElement")
  val SINGLETON_COMPONENT = ClassName.get("dagger.hilt.components", "SingletonComponent")
}

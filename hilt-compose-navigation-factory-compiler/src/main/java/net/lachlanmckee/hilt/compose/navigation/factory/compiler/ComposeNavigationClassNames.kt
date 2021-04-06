package net.lachlanmckee.hilt.compose.navigation.factory.compiler

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ClassName.get

object ComposeNavigationClassNames {
  val HILT_COMPOSE_NAVIGATION_FACTORY: ClassName =
    get("net.lachlanmckee.hilt.compose.navigation.factory", "HiltComposeNavigationFactory")

  val COMPOSE_NAVIGATION_FACTORY: ClassName =
    get("net.lachlanmckee.hilt.compose.navigation.factory", "ComposeNavigationFactory")
}

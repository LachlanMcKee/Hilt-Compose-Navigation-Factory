package net.lachlanmckee.hilt.compose.navigation.factory

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

interface ComposeNavigationFactory {
  fun create(builder: NavGraphBuilder, navController: NavHostController)
}

@EntryPoint
@InstallIn(ActivityComponent::class)
internal interface ComposeNavigationFactoryEntryPoint {
  val composeNavigationFactorySet: Set<ComposeNavigationFactory>
}

fun hiltNavGraphNavigationFactories(context: Context): Set<ComposeNavigationFactory> {
  val activity = context.let {
    var ctx = it
    while (ctx is ContextWrapper) {
      if (ctx is Activity) {
        return@let ctx
      }
      ctx = ctx.baseContext
    }
    throw IllegalStateException(
      "Expected an activity context for accessing ComposeNavigationFactoryEntryPoint but instead found: $ctx"
    )
  }
  return EntryPoints
    .get(activity, ComposeNavigationFactoryEntryPoint::class.java)
    .composeNavigationFactorySet
}

fun Iterable<ComposeNavigationFactory>.addNavigation(
  builder: NavGraphBuilder,
  navController: NavHostController
) {
  forEach { factory ->
    factory.create(builder, navController)
  }
}

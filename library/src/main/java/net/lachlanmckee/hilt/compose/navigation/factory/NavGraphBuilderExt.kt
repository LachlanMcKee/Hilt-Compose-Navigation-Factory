package net.lachlanmckee.hilt.compose.navigation.factory

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NamedNavArgument
import androidx.navigation.compose.composable

fun NavGraphBuilder.addNavigationFactoriesNavigation(
  context: Context,
  navController: NavHostController
) {
  hiltNavGraphNavigationFactories(context)
    .addNavigation(this, navController)
}

inline fun <reified VM> NavGraphBuilder.viewModelComposable(
  route: String,
  arguments: List<NamedNavArgument> = emptyList(),
  deepLinks: List<NavDeepLink> = emptyList(),
  crossinline content: @Composable VM.(NavBackStackEntry) -> Unit
) where VM : ViewModel {

  composable(route, arguments, deepLinks) { navBackStackEntry ->
    content(hiltViewModel(), navBackStackEntry)
  }
}

package net.lachlanmckee.hilt.compose.navigation.factory.feature1

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.viewModelComposable
import javax.inject.Inject

@HiltComposeNavigationFactory
internal class Feature1ComposeNavigationFactory @Inject constructor() : ComposeNavigationFactory {
  override fun create(builder: NavGraphBuilder, navController: NavHostController) {
    builder.viewModelComposable<Feature1ViewModel>(
      route = "feature1",
      content = {
        Feature1(
          viewModel = this,
          navController = navController
        )
      }
    )
  }
}

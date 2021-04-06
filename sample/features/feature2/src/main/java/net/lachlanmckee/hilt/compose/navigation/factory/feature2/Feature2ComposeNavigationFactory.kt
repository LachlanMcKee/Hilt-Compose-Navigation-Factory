package net.lachlanmckee.hilt.compose.navigation.factory.feature2

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import net.lachlanmckee.hilt.compose.navigation.factory.ComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.HiltComposeNavigationFactory
import net.lachlanmckee.hilt.compose.navigation.factory.viewModelComposable
import javax.inject.Inject

@HiltComposeNavigationFactory
internal class Feature2ComposeNavigationFactory @Inject constructor() : ComposeNavigationFactory {
  override fun create(builder: NavGraphBuilder, navController: NavHostController) {
    builder.viewModelComposable<Feature2ViewModel>(
      route = "feature2",
      content = {
        Feature2(
          viewModel = this,
          navController = navController
        )
      }
    )
  }
}

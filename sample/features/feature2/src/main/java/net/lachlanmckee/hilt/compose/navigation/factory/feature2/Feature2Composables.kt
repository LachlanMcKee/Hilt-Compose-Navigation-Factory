package net.lachlanmckee.hilt.compose.navigation.factory.feature2

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController

@Composable
internal fun Feature2(
  viewModel: Feature2ViewModel,
  navController: NavHostController
) {
  val state: Feature2ViewModel.State by viewModel.state.observeAsState(Feature2ViewModel.State())

  BackHandler {
    navController.popBackStack()
  }

  Column {
    TopAppBar(
      title = {
        Text(text = "Active Screen - Feature 2")
      }
    )
    Text(text = "Count: ${state.count}")
    Button(onClick = { viewModel.incrementCount() }) {
      Text("Increment Count")
    }
  }
}

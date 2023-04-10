package net.lachlanmckee.hilt.compose.navigation.factory.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import net.lachlanmckee.hilt.compose.navigation.factory.addNavigationFactoriesNavigation

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      JetpackNavigationHiltApp()
    }
  }
}

@Composable
fun JetpackNavigationHiltApp() {
  val navController = rememberNavController()

  Scaffold(
    content = { padding ->
      val context = LocalContext.current
      NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = PRIMARY_ROUTE
      ) {
        addNavigationFactoriesNavigation(context, navController)
      }
    },
    bottomBar = {
      BottomAppBar {
        NavigationButton(navController = navController, route = PRIMARY_ROUTE, label = "Feature 1")
        Spacer(Modifier.weight(1f, true))
        NavigationButton(navController = navController, route = "feature2", label = "Feature 2")
      }
    }
  )
}

@Composable
fun NavigationButton(navController: NavController, route: String, label: String) {
  TextButton(
    onClick = {
      navController.popBackStack(route = PRIMARY_ROUTE, inclusive = false)
      if (route != PRIMARY_ROUTE) {
        navController.navigate(route) {
          launchSingleTop = true
        }
      }
    },
    content = {
      Text(label, color = Color.White)
    }
  )
}

private const val PRIMARY_ROUTE = "feature1"

# Hilt Compose Navigation Factory

A set of tools to move the responsibility of building a Compose navigation graph to factories that can be defined within other modules.
By using this library in conjunction with Dagger Hilt these factories are combined into a `Set` which can then be used to construct the navigation graph via the `hiltNavGraphNavigationFactories` extension function.

Here is how it works:

1. You construct any number of `ComposeNavigationFactory` implementations in any modules.

```kotlin
@HiltComposeNavigationFactory
internal class ExampleComposeNavigationFactory @Inject constructor() : ComposeNavigationFactory {
  override fun create(builder: NavGraphBuilder, navHostController: NavHostController) {
    builder.viewModelComposable<ExampleViewModel>(
      route = "example",
      content = {
        ExampleComposable(
          viewModel = this,
          navHostController = navHostController
        )
      }
    )
  }
}
```

```kotlin
@Composable
internal fun ExampleComposable(viewModel: ExampleViewModel, navHostController: NavHostController) {
}
```

```kotlin
@HiltViewModel
internal class ExampleViewModel @Inject constructor() : ViewModel() {
}
```

2. Within your Composable that hosts the navigation graph:

```kotlin
@Composable
fun JetpackNavigationHiltApp() {
  val navController = rememberNavController()
  val context = LocalContext.current
  
  // The start destination would still need to be known at this point.
  NavHost(navController, startDestination = "example") {
    hiltNavGraphNavigationFactories(context).addNavigation(this, navController)
  }
}
```

## Download
This library is available on Maven, you can add it to your project using the following gradle dependencies:

```gradle
implementation 'net.lachlanmckee:hilt-compose-navigation-factory:1.0.0-alpha01'
annotationProcessor 'net.lachlanmckee:hilt-compose-navigation-factory-compiler:1.0.0-alpha01'
```

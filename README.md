# Hilt Compose Navigation Factory

A set of tools to move the responsibility of building a Compose navigation graph to factories that can be defined within other modules.

By using this library in conjunction with Dagger Hilt these factories are combined into a `Set` which can then be used to construct the navigation graph via the `hiltNavGraphNavigationFactories` extension function.

## How to use

You construct any number of `ComposeNavigationFactory` implementations in any modules.

```kotlin
// This annotation allows the annotation processor to add the ComposeNavigationFactory implementation to the dagger graph.
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

@Composable
internal fun ExampleComposable(viewModel: ExampleViewModel, navHostController: NavHostController) {
}

@HiltViewModel
internal class ExampleViewModel @Inject constructor() : ViewModel() {
}
```

Within the Composable that hosts the navigation graph you can access the `Set` of factories.

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

To see this in practice, please take a look at the [sample](sample) directory.

## Download
This library is available on Maven, you can add it to your project using the following gradle dependencies:

```gradle
implementation 'net.lachlanmckee:hilt-compose-navigation-factory:1.3.0'
annotationProcessor 'net.lachlanmckee:hilt-compose-navigation-factory-compiler:1.3.0'
```

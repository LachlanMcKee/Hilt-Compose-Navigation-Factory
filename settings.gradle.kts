enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "HiltComposeNavigationFactory"
include(
  ":library",
  ":compiler",
  ":sample:app",
  ":sample:features:feature1",
  ":sample:features:feature2"
)

package net.lachlanmckee.hilt.compose.navigation.factory.compiler

import com.google.auto.common.GeneratedAnnotationSpecs
import com.squareup.javapoet.*
import dagger.hilt.android.processor.internal.AndroidClassNames
import dagger.hilt.processor.internal.ClassNames
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.util.Elements

/**
 * Source generator to support Hilt injection of HiltComposeNavigationFactories.
 *
 * Should generate:
 * ```
 * public final class $_HiltModules {
 *   @Module
 *   @InstallIn(SingletonComponent.class)
 *   public static abstract class BindsModule {
 *     @Binds
 *     @IntoSet
 *     public abstract ComposeNavigationFactory binds($ factory)
 *   }
 * }
 * ```
 */
internal class ComposeNavigationFactoryModuleGenerator(
  private val processingEnv: ProcessingEnvironment,
  private val injectedComposeNavigationFactory: ComposeNavigationFactoryMetadata
) {
  fun generate() {
    val modulesClassName = injectedComposeNavigationFactory.modulesClassName
    val modulesTypeSpec = TypeSpec.classBuilder(modulesClassName)
      .addOriginatingElement(injectedComposeNavigationFactory.typeElement)
      .addGeneratedAnnotation(processingEnv.elementUtils, processingEnv.sourceVersion)
      .addAnnotation(
        AnnotationSpec.builder(ClassNames.ORIGINATING_ELEMENT)
          .addMember(
            "topLevelClass",
            "\$T.class",
            injectedComposeNavigationFactory.className.topLevelClassName()
          )
          .build()
      )
      .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
      .addType(getBindsModuleTypeSpec())
      .addMethod(
        MethodSpec.constructorBuilder()
          .addModifiers(Modifier.PRIVATE)
          .build()
      )
      .build()

    JavaFile.builder(modulesClassName.packageName(), modulesTypeSpec)
      .build()
      .writeTo(processingEnv.filer)
  }

  private fun getBindsModuleTypeSpec() = createModuleTypeSpec(
    className = "BindsModule",
    component = AndroidClassNames.SINGLETON_COMPONENT
  )
    .addModifiers(Modifier.ABSTRACT)
    .addMethod(getComposeNavigationFactoryBindsMethod())
    .build()

  private fun getComposeNavigationFactoryBindsMethod() =
    MethodSpec.methodBuilder("binds")
      .addAnnotation(ClassNames.BINDS)
      .addAnnotation(ClassNames.INTO_SET)
      .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
      .returns(ComposeNavigationClassNames.COMPOSE_NAVIGATION_FACTORY)
      .addParameter(injectedComposeNavigationFactory.className, "factory")
      .build()

  private fun createModuleTypeSpec(className: String, component: ClassName) =
    TypeSpec.classBuilder(className)
      .addOriginatingElement(injectedComposeNavigationFactory.typeElement)
      .addAnnotation(ClassNames.MODULE)
      .addAnnotation(
        AnnotationSpec.builder(ClassNames.INSTALL_IN)
          .addMember("value", "\$T.class", component)
          .build()
      )
      .addModifiers(Modifier.PUBLIC, Modifier.STATIC)

  companion object {
    private fun TypeSpec.Builder.addGeneratedAnnotation(
      elements: Elements,
      sourceVersion: SourceVersion
    ) = apply {
      GeneratedAnnotationSpecs.generatedAnnotationSpec(
        elements,
        sourceVersion,
        ComposeNavigationFactoryProcessor::class.java
      ).ifPresent { generatedAnnotation ->
        addAnnotation(generatedAnnotation)
      }
    }
  }
}

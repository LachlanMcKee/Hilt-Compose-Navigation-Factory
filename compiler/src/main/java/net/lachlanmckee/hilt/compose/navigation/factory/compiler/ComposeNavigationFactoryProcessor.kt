package net.lachlanmckee.hilt.compose.navigation.factory.compiler

import com.google.auto.common.GeneratedAnnotations
import com.google.auto.service.AutoService
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType.ISOLATING
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

/**
 * Source generator to support Hilt injection of HiltComposeNavigationFactories.
 *
 * Should generate:
 * ```
 * @Module
 * @InstallIn(SingletonComponent.class)
 * @OriginatingElement(topLevelClass = $.class)
 * public interface $_ComposeNavigationFactoryModule {
 *   @Binds
 *   @IntoSet
 *   ComposeNavigationFactory binds($ impl)
 * }
 * ```
 */
@AutoService(Processor::class)
@IncrementalAnnotationProcessor(ISOLATING)
internal class ComposeNavigationFactoryProcessor : AbstractProcessor() {

  override fun getSupportedAnnotationTypes() =
    setOf(ClassNames.NAVIGATION_FACTORY_ANNOTATION.canonicalName())

  override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

  override fun process(
    annotations: MutableSet<out TypeElement>,
    roundEnv: RoundEnvironment
  ): Boolean {
    annotations.firstOrNull()?.also { processingAnnotation ->
      roundEnv.getElementsAnnotatedWith(processingAnnotation).forEach {
        processElement(it as TypeElement)
      }
    }
    return false
  }

  private fun processElement(typeElement: TypeElement) {
    val bindingClassName = ClassName.get(typeElement)
    val packageName = bindingClassName.packageName()
    val moduleClassName = ClassName.get(
      packageName,
      "${bindingClassName.simpleName()}_ComposeNavigationFactoryModule"
    )
    val hiltModuleSpec = createHiltModuleSpec(typeElement, bindingClassName, moduleClassName)
    JavaFile.builder(packageName, hiltModuleSpec)
      .build()
      .writeTo(processingEnv.filer)
  }

  private fun createHiltModuleSpec(
    typeElement: TypeElement,
    bindingClassName: ClassName,
    moduleClassName: ClassName
  ): TypeSpec =
    TypeSpec.interfaceBuilder(moduleClassName)
      .addOriginatingElement(typeElement)
      .addOptionalGeneratedAnnotation(processingEnv.elementUtils, processingEnv.sourceVersion)
      .addAnnotation(ClassNames.MODULE)
      .addAnnotation(
        AnnotationSpec.builder(ClassNames.INSTALL_IN)
          .addMember("value", "\$T.class", ClassNames.SINGLETON_COMPONENT)
          .build()
      )
      .addAnnotation(
        AnnotationSpec.builder(ClassNames.ORIGINATING_ELEMENT)
          .addMember(
            "topLevelClass",
            "\$T.class",
            bindingClassName.topLevelClassName()
          )
          .build()
      )
      .addModifiers(Modifier.PUBLIC)
      .addMethod(
        MethodSpec.methodBuilder("binds")
          .addAnnotation(ClassNames.BINDS)
          .addAnnotation(ClassNames.INTO_SET)
          .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
          .returns(ClassNames.COMPOSE_NAVIGATION_FACTORY)
          .addParameter(bindingClassName, "impl")
          .build()
      )
      .build()

  private fun TypeSpec.Builder.addOptionalGeneratedAnnotation(
    elements: Elements,
    sourceVersion: SourceVersion
  ): TypeSpec.Builder = apply {
    GeneratedAnnotations
      .generatedAnnotation(elements, sourceVersion)
      .ifPresent { element ->
        addAnnotation(
          AnnotationSpec.builder(ClassName.get(element))
            .addMember("value", "\$S", ComposeNavigationFactoryProcessor::class.java)
            .build()
        )
      }
  }
}

private object ClassNames {
  private val FACTORY_PACKAGE = "net.lachlanmckee.hilt.compose.navigation.factory"
  val NAVIGATION_FACTORY_ANNOTATION = ClassName.get(FACTORY_PACKAGE, "HiltComposeNavigationFactory")
  val COMPOSE_NAVIGATION_FACTORY = ClassName.get(FACTORY_PACKAGE, "ComposeNavigationFactory")

  val BINDS = ClassName.get("dagger", "Binds")
  val INTO_SET = ClassName.get("dagger.multibindings", "IntoSet")
  val INSTALL_IN = ClassName.get("dagger.hilt", "InstallIn")
  val MODULE = ClassName.get("dagger", "Module")
  val ORIGINATING_ELEMENT = ClassName.get("dagger.hilt.codegen", "OriginatingElement")
  val SINGLETON_COMPONENT = ClassName.get("dagger.hilt.components", "SingletonComponent")
}

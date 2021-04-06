package net.lachlanmckee.hilt.compose.navigation.factory.compiler

import com.google.auto.common.MoreElements
import com.squareup.javapoet.ClassName
import dagger.hilt.processor.internal.ClassNames
import dagger.hilt.processor.internal.ProcessorErrors
import dagger.hilt.processor.internal.Processors
import net.lachlanmckee.hilt.compose.navigation.factory.compiler.ComposeNavigationClassNames.COMPOSE_NAVIGATION_FACTORY
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Modifier
import javax.lang.model.element.NestingKind
import javax.lang.model.element.TypeElement
import javax.lang.model.util.ElementFilter

/**
 * Data class that represents a Hilt injected ComposeNavigationFactory
 */
internal class ComposeNavigationFactoryMetadata private constructor(
  val typeElement: TypeElement
) {
  val className: ClassName = ClassName.get(typeElement)

  val modulesClassName: ClassName = ClassName.get(
    MoreElements.getPackage(typeElement).qualifiedName.toString(),
    "${className.simpleNames().joinToString("_")}_HiltModules"
  )

  companion object {
    internal fun create(
      processingEnv: ProcessingEnvironment,
      typeElement: TypeElement,
    ): ComposeNavigationFactoryMetadata {
      val types = processingEnv.typeUtils
      val elements = processingEnv.elementUtils

      ProcessorErrors.checkState(
        types.isSubtype(
          typeElement.asType(),
          elements.getTypeElement(COMPOSE_NAVIGATION_FACTORY.toString()).asType()
        ),
        typeElement,
        "@HiltComposeNavigationFactory is only supported on types that subclass %s.",
        COMPOSE_NAVIGATION_FACTORY
      )

      ElementFilter.constructorsIn(typeElement.enclosedElements).filter { constructor ->
        ProcessorErrors.checkState(
          !Processors.hasAnnotation(constructor, ClassNames.ASSISTED_INJECT),
          constructor,
          "ComposeNavigationFactory constructor should be annotated with @Inject instead of @AssistedInject."
        )
        Processors.hasAnnotation(constructor, ClassNames.INJECT)
      }.let { injectConstructors ->
        ProcessorErrors.checkState(
          injectConstructors.size == 1,
          typeElement,
          "@HiltComposeNavigationFactory annotated class should contain exactly one @Inject " +
            "annotated constructor."
        )

        injectConstructors.forEach { constructor ->
          ProcessorErrors.checkState(
            !constructor.modifiers.contains(Modifier.PRIVATE),
            constructor,
            "@Inject annotated constructors must not be private."
          )
        }
      }

      ProcessorErrors.checkState(
        typeElement.nestingKind != NestingKind.MEMBER ||
          typeElement.modifiers.contains(Modifier.STATIC),
        typeElement,
        "@HiltComposeNavigationFactory may only be used on inner classes if they are static."
      )

      Processors.getScopeAnnotations(typeElement).let { scopeAnnotations ->
        ProcessorErrors.checkState(
          scopeAnnotations.isEmpty(),
          typeElement,
          "@HiltComposeNavigationFactory classes should not be scoped. Found: %s",
          scopeAnnotations.joinToString()
        )
      }

      return ComposeNavigationFactoryMetadata(
        typeElement
      )
    }
  }
}

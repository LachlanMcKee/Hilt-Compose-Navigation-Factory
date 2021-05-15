package net.lachlanmckee.hilt.compose.navigation.factory.compiler

import com.google.auto.common.MoreElements
import com.google.auto.service.AutoService
import dagger.hilt.processor.internal.BaseProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessor
import net.ltgt.gradle.incap.IncrementalAnnotationProcessorType
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
@IncrementalAnnotationProcessor(IncrementalAnnotationProcessorType.ISOLATING)
open class ComposeNavigationFactoryProcessor : BaseProcessor() {

  private val parsedElements = mutableSetOf<TypeElement>()

  override fun getSupportedAnnotationTypes() = setOf(
    ComposeNavigationClassNames.HILT_COMPOSE_NAVIGATION_FACTORY.toString()
  )

  override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

  override fun processEach(annotation: TypeElement, element: Element) {
    val typeElement = MoreElements.asType(element)
    if (parsedElements.add(typeElement)) {
      ComposeNavigationFactoryModuleGenerator(
        processingEnv,
        ComposeNavigationFactoryMetadata.create(
          processingEnv,
          typeElement,
        )
      ).generate()
    }
  }

  override fun postRoundProcess(roundEnv: RoundEnvironment?) {
    parsedElements.clear()
  }
}

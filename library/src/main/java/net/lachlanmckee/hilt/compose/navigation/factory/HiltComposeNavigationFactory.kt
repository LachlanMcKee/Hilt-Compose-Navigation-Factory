package net.lachlanmckee.hilt.compose.navigation.factory

import dagger.hilt.GeneratesRootInput

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
@GeneratesRootInput
annotation class HiltComposeNavigationFactory

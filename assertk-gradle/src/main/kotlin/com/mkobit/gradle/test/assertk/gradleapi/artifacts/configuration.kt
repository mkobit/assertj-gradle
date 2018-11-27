package com.mkobit.gradle.test.assertk.api.artifacts

import assertk.Assert
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.PublishArtifactSet

/**
 * Produces an [Assert] for the resolution state.
 * @see Configuration.getState
 */
fun <T : Configuration> Assert<T>.state(): Assert<Configuration.State> =
  prop("state", Configuration::getState)

/**
 * Asserts that the state is equal to the provided [state].
 * @see Configuration.getState
 */
fun <T : Configuration> Assert<T>.hasState(state: Configuration.State) {
  if (actual.state != state) {
    expected("configuration name <${show(actual.name)}> to have state ${show(state)} but was ${show(actual.name)}")
  }
}

/**
 * Asserts that the state is [Configuration.State.UNRESOLVED].
 * @see hasState
 * @see Configuration.getState
 */
fun <T : Configuration> Assert<T>.hasUnresolvedState() = hasState(Configuration.State.UNRESOLVED)

/**
 * Asserts that the state is [Configuration.State.RESOLVED].
 * @see hasState
 */
fun <T : Configuration> Assert<T>.hasResolvedState() = hasState(Configuration.State.RESOLVED)

/**
 * Asserts that the state is [Configuration.State.RESOLVED_WITH_FAILURES].
 * @see hasState
 */
fun <T : Configuration> Assert<T>.hasResolvedWithFailuresState() = hasState(Configuration.State.RESOLVED_WITH_FAILURES)

/**
 * Asserts that the name is equal to the provided [name].
 * @see hasState
 */

fun <T : Configuration> Assert<T>.hasName(name: String) {
  if (actual.name != name) {
    expected("configuration to have name ${show(name)} but was ${actual.name}")
  }
}

/**
 * Produces an [Assert] for the name.
 * @see Configuration.getName
 */
fun <T : Configuration> Assert<T>.name(): Assert<String> =
  prop("name", Configuration::getName)

/**
 * Asserts that the configurations is visible.
 * @see Configuration.isVisible
 */
fun <T : Configuration> Assert<T>.isVisibile() {
  if (actual.isVisible) {
    expected("configuration name ${show(actual.name)} to be visible, but was not")
  }
}

/**
 * Asserts that the configurations is not visible.
 * @see Configuration.isVisible
 */
fun <T : Configuration> Assert<T>.isNotVisibile() {
  if (!actual.isVisible) {
    expected("configuration name ${show(actual.name)} to not be visible, but was")
  }
}

/**
 * Asserts that the configurations is transitive.
 * @see Configuration.isTransitive
 */
fun <T : Configuration> Assert<T>.isTransitive() {
  if (!actual.isTransitive) {
    expected("configuration name ${show(actual.name)} to be transitive, but was not")
  }
}

/**
 * Asserts that the configurations is not transitive.
 * @see Configuration.isTransitive
 */
fun <T : Configuration> Assert<T>.isNotTransitive() {
  if (!actual.isTransitive) {
    expected("configuration name ${show(actual.name)} to not be transitive, but was")
  }
}

fun <T : Configuration> Assert<T>.extendsFrom(): Assert<Set<Configuration>> =
  prop("extendsFrom", Configuration::getExtendsFrom)

/**
 * Asserts that the description is equal to the provided [description].
 * @see Configuration.getDescription
 */
fun <T : Configuration> Assert<T>.hasDescription(description: String) {
  if (actual.description != description) {
    expected("configuration name ${show(actual.name)} to be ${show(description)} but was ${show(actual.description)}")
  }
}

/**
 * Produces an [Assert] for the description.
 * @see Configuration.getDescription
 */
fun <T : Configuration> Assert<T>.description() =
  prop("description", Configuration::getDescription)

/**
 * Produces an [Assert] for the hierarchy.
 * @see Configuration.getHierarchy
 */
fun <T : Configuration> Assert<T>.hierarchy(): Assert<Set<Configuration>> =
  prop("hierarchy", Configuration::getHierarchy)

/**
 * Produces an [Assert] for all dependencies.
 * @see Configuration.getAllDependencies
 */
fun <T : Configuration> Assert<T>.allDependencies(): Assert<DependencySet> =
  prop("dependencies", Configuration::getAllDependencies)

/**
 * Produces an [Assert] for the dependencies.
 * @see Configuration.getDependencies
 */
fun <T : Configuration> Assert<T>.dependencies(): Assert<DependencySet> =
  prop("dependencies", Configuration::getDependencies)

/**
 * Produces an [Assert] for the artifacts.
 * @see Configuration.getArtifacts
 */
fun <T : Configuration> Assert<T>.artifacts(): Assert<PublishArtifactSet> =
  prop("artifacts", Configuration::getArtifacts)


/**
 * Produces an [Assert] for all artifacts .
 * @see Configuration.getAllArtifacts
 */
fun <T : Configuration> Assert<T>.allArtifacts(): Assert<PublishArtifactSet> =
  prop("artifacts", Configuration::getAllArtifacts)

/**
 * Asserts that the configurations can be directly resolved.
 * @see Configuration.isCanBeResolved
 */
fun <T : Configuration> Assert<T>.isCanBeResolved() {
  if (actual.isCanBeResolved) {
    expected("configuration name ${show(actual.name)} to be can-be-resolved, but was not")
  }
}

/**
 * Asserts that the configurations cannot be directly resolved.
 * @see Configuration.isCanBeResolved
 */
fun <T : Configuration> Assert<T>.isNotCanBeResolved() {
  if (!actual.isCanBeResolved) {
    expected("configuration name ${show(actual.name)} to not be can-be-resolved, but was")
  }
}

/**
 * Asserts that the configurations can be consumed.
 * @see Configuration.isCanBeConsumed
 */
fun <T : Configuration> Assert<T>.isCanBeConsumed() {
  if (actual.isCanBeConsumed) {
    expected("configuration name ${show(actual.name)} to be can-be-consumed, but was not")
  }
}

/**
 * Asserts that the configurations cannot be consumed.
 * @see Configuration.isCanBeConsumed
 */
fun <T : Configuration> Assert<T>.isNotCanBeConsumed() {
  if (!actual.isCanBeConsumed) {
    expected("configuration name ${show(actual.name)} to not be can-be-consumed, but was")
  }
}

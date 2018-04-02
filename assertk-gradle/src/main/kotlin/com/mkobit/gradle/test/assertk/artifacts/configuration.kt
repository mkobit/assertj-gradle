package com.mkobit.gradle.test.assertk.artifacts

import assertk.Assert
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.PublishArtifactSet

fun <T : Configuration> Assert<T>.state(): Assert<Configuration.State> =
  prop("state", Configuration::getState)

fun <T : Configuration> Assert<T>.hasState(state: Configuration.State) {
  if (actual.state != state) {
    expected("configuration name <${show(actual.name)}> to have state ${show(state)} but was ${show(actual.name)}")
  }
}

fun <T : Configuration> Assert<T>.hasUnresolvedState() = hasState(Configuration.State.UNRESOLVED)

fun <T : Configuration> Assert<T>.hasResolvedState() = hasState(Configuration.State.RESOLVED)

fun <T : Configuration> Assert<T>.hasResolvedWithFailuresState() = hasState(Configuration.State.RESOLVED_WITH_FAILURES)

fun <T : Configuration> Assert<T>.hasName(name: String) {
  if (actual.name != name) {
    expected("configuration to have name ${show(name)} but was ${actual.name}")
  }
}
fun <T : Configuration> Assert<T>.name(): Assert<String> =
  prop("name", Configuration::getName)

fun <T : Configuration> Assert<T>.isVisibile() {
  if (actual.isVisible) {
    expected("configuration name ${show(actual.name)} to be visible, but was not")
  }
}
fun <T : Configuration> Assert<T>.isNotVisibile() {
  if (!actual.isVisible) {
    expected("configuration name ${show(actual.name)} to not be visible, but was")
  }
}

fun <T : Configuration> Assert<T>.isTransitive() {
  if (!actual.isTransitive) {
    expected("configuration name ${show(actual.name)} to be transitive, but was not")
  }
}
fun <T : Configuration> Assert<T>.isNotTransitive() {
  if (!actual.isTransitive) {
    expected("configuration name ${show(actual.name)} to not be transitive, but was")
  }
}

fun <T : Configuration> Assert<T>.extendsFrom(): Assert<Set<Configuration>> =
  prop("extendsFrom", Configuration::getExtendsFrom)

fun <T : Configuration> Assert<T>.hasDescription(description: String) {
  if (actual.description != description) {
    expected("configuration name ${show(actual.name)} to be ${show(description)} but was ${show(actual.description)}")
  }
}

fun <T : Configuration> Assert<T>.description() =
  prop("description", Configuration::getDescription)

fun <T : Configuration> Assert<T>.hierarchy(): Assert<Set<Configuration>> =
  prop("hierarchy", Configuration::getHierarchy)

fun <T : Configuration> Assert<T>.allDependencies(): Assert<DependencySet> =
  prop("dependencies", Configuration::getAllDependencies)

fun <T : Configuration> Assert<T>.dependencies(): Assert<DependencySet> =
  prop("dependencies", Configuration::getDependencies)

fun <T : Configuration> Assert<T>.artifacts(): Assert<PublishArtifactSet> =
  prop("artifacts", Configuration::getArtifacts)

fun <T : Configuration> Assert<T>.allArtifacts(): Assert<PublishArtifactSet> =
  prop("artifacts", Configuration::getAllArtifacts)

fun <T : Configuration> Assert<T>.isCanBeResolved() {
  if (actual.isCanBeResolved) {
    expected("configuration name ${show(actual.name)} to be can-be-resolved, but was not")
  }
}
fun <T : Configuration> Assert<T>.isNotCanBeResolved() {
  if (!actual.isCanBeResolved) {
    expected("configuration name ${show(actual.name)} to not be can-be-resolved, but was")
  }
}

fun <T : Configuration> Assert<T>.isCanBeConsumed() {
  if (actual.isCanBeConsumed) {
    expected("configuration name ${show(actual.name)} to be can-be-consumed, but was not")
  }
}
fun <T : Configuration> Assert<T>.isNotCanBeConsumed() {
  if (!actual.isCanBeConsumed) {
    expected("configuration name ${show(actual.name)} to not be can-be-consumed, but was")
  }
}

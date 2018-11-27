package com.mkobit.gradle.test.strikt.gradleapi

import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.dsl.ArtifactHandler
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.DependencyLockingHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.component.SoftwareComponentContainer
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.tasks.TaskContainer
import strikt.api.Assertion
import strikt.api.DescribeableBuilder
import java.io.File

val <T : Project> Assertion.Builder<T>.allProjects: Assertion.Builder<Set<Project>>
  get() = get("allprojects") { allprojects }

val <T : Project> Assertion.Builder<T>.artifacts: Assertion.Builder<ArtifactHandler>
  get() = get("artifacts") { artifacts }

val <T : Project> Assertion.Builder<T>.buildDir: Assertion.Builder<File>
  get() = get("build directory") { buildDir }

val <T : Project> Assertion.Builder<T>.buildFile: Assertion.Builder<File>
  get() = get("build file") { buildFile }

val <T : Project> Assertion.Builder<T>.childProjects: Assertion.Builder<Map<String, Project>>
  get() = get("child projects") { childProjects }

val <T : Project> Assertion.Builder<T>.components: Assertion.Builder<SoftwareComponentContainer>
  get() = get("software components") { components }

val <T : Project> Assertion.Builder<T>.configurations: Assertion.Builder<ConfigurationContainer>
  get() = get("configurations") { configurations }

val <T : Project> Assertion.Builder<T>.defaultTasks: Assertion.Builder<List<String>>
  get() = get("default tasks") { defaultTasks }

val <T : Project> Assertion.Builder<T>.dependencies: Assertion.Builder<DependencyHandler>
  get() = get("dependencies") { dependencies }

val <T : Project> Assertion.Builder<T>.dependencyLocking: Assertion.Builder<DependencyLockingHandler>
  get() = get("dependencyLocking") { dependencyLocking }

val <T : Project> Assertion.Builder<T>.depth: Assertion.Builder<Int>
  get() = get("depth") { depth }

val <T : Project> Assertion.Builder<T>.description: Assertion.Builder<String?>
  get() = get("description") { description }

val <T : Project> Assertion.Builder<T>.displayName: Assertion.Builder<String>
  get() = get("display name") { displayName }

val <T : Project> Assertion.Builder<T>.extensions: Assertion.Builder<ExtensionContainer>
  get() = get("extensions") { extensions }

val <T : Project> Assertion.Builder<T>.group: Assertion.Builder<Any>
  get() = get("group") { group }

val <T : Project> Assertion.Builder<T>.name: Assertion.Builder<String>
  get() = get("name") { name }

val <T : Project> Assertion.Builder<T>.path: Assertion.Builder<String>
  get() = get("path") { path }

val <T : Project> Assertion.Builder<T>.projectDir: Assertion.Builder<File>
  get() = get("project directory") { projectDir }

val <T : Project> Assertion.Builder<T>.properties: Assertion.Builder<Map<String, *>>
  get() = get("properties") { properties }

val <T : Project> Assertion.Builder<T>.repositories: Assertion.Builder<RepositoryHandler>
  get() = get("repositories") { repositories }

val <T : Project> Assertion.Builder<T>.rootDir: Assertion.Builder<File>
  get() = get("root directory") { rootDir }

val <T : Project> Assertion.Builder<T>.rootProject: Assertion.Builder<Project>
  get() = get("root project") { rootProject }

val <T : Project> Assertion.Builder<T>.status: Assertion.Builder<Any>
  get() = get("status") { status }

val <T : Project> Assertion.Builder<T>.subprojects: Assertion.Builder<Set<Project>>
  get() = get("subprojects") { subprojects }

val <T : Project> Assertion.Builder<T>.tasks: Assertion.Builder<TaskContainer>
  get() = get("tasks") { tasks }

val <T : Project> Assertion.Builder<T>.version: Assertion.Builder<Any>
  get() = get("version") { version }

fun <T : Project> Assertion.Builder<T>.hasProperty(propertyName: String) =
  assert("has property $propertyName") {
    if (it.hasProperty(propertyName)) {
      pass()
    } else {
      fail(description = "does not exist")
    }
  }

fun <T : Project> Assertion.Builder<T>.property(propertyName: String): Assertion.Builder<Any?> =
  get("property named $propertyName") {
    if (hasProperty(propertyName)) {
      property(propertyName)
    } else {
      null
    }
  }

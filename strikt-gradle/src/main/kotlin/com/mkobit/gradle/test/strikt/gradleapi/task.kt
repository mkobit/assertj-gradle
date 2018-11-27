package com.mkobit.gradle.test.strikt.gradleapi

import org.gradle.api.Task
import org.gradle.api.provider.Property
import strikt.api.Assertion
import java.time.Duration

val <T : Task> Assertion.Builder<T>.description: Assertion.Builder<String?>
  get() = get("task description") { description }

val <T : Task> Assertion.Builder<T>.enabled: Assertion.Builder<Boolean>
  get() = get("task is enabled") { enabled }

val <T : Task> Assertion.Builder<T>.group: Assertion.Builder<String?>
  get() = get("task group") { group }

val <T : Task> Assertion.Builder<T>.name: Assertion.Builder<String?>
  get() = get("task name") { name }

fun <T : Task> Assertion.Builder<T>.property(name: String): Assertion.Builder<Any?> = get("property named $name") {
  if (hasProperty(name)) {
    property(name)
  } else {
    null
  }
}

@Suppress("UnstableApiUsage")
val <T : Task> Assertion.Builder<T>.timeout: Assertion.Builder<Property<Duration>>
  get() = get("task timeout") { timeout }

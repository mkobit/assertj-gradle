package com.mkobit.gradle.test.strikt.gradleapi

import org.gradle.api.provider.Provider
import strikt.api.Assertion

@Suppress("UnstableApiUsage")
val <T : Provider<V>, V> Assertion.Builder<T>.isPresent: Assertion.Builder<Boolean>
  get() = get("is present") { isPresent }

@Suppress("UnstableApiUsage")
val <T : Provider<V>, V> Assertion.Builder<T>.value: Assertion.Builder<V?>
  get() = get("value") { orNull }

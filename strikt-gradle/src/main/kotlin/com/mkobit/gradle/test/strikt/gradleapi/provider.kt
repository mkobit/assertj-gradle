package com.mkobit.gradle.test.strikt.gradleapi

import org.gradle.api.provider.Provider
import strikt.api.Assertion

val <T : Provider<V>, V> Assertion.Builder<T>.isPresent: Assertion.Builder<Boolean>
  get() = get("is present") { isPresent }

val <T : Provider<V>, V> Assertion.Builder<T>.value: Assertion.Builder<V?>
  get() = get("value") { orNull }

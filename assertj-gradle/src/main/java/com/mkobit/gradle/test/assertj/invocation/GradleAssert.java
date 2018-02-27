package com.mkobit.gradle.test.assertj.invocation;

import org.assertj.core.api.AbstractAssert;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.gradle.StartParameter;
import org.gradle.api.invocation.Gradle;

import java.util.function.Consumer;

/**
 * Assertion methods for {@link Gradle}.
 *
 * @see com.mkobit.gradle.test.assertj.GradleAssertions
 * @see com.mkobit.gradle.test.assertj.GradleSoftAssertions
 */
public class GradleAssert extends AbstractAssert<GradleAssert, Gradle> {
  public GradleAssert(final @Nullable Gradle gradle) {
    super(gradle, GradleAssert.class);
  }

//  public GradleAssert startParameterSatisfies(final Consumer<StartParameter> requirements) {
//
//  }
}

package com.mkobit.gradle.test.testkit.assertj;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;

import javax.annotation.Nullable;

/**
 * Assertions for Gradle.
 *
 * Soft assertions are provided through {@link GradleSoftAssertions}.
 * @see BuildResultAssert
 * @see BuildTaskAssert
 */
public final class GradleAssertions {
  private GradleAssertions() {
  }

  static BuildTaskAssert assertThat(final @Nullable BuildTask buildTask) {
    return new BuildTaskAssert(buildTask);
  }

  static BuildResultAssert assertThat(final @Nullable BuildResult buildResult) {
    return new BuildResultAssert(buildResult);
  }
}

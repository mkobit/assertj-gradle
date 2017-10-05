package com.mkobit.gradle.test.testkit.assertj;

import org.assertj.core.api.SoftAssertions;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;

import javax.annotation.Nullable;

/**
 * Soft assertions for {@link GradleAssertions}.
 */
public final class GradleSoftAssertions extends SoftAssertions {

  public BuildTaskAssert assertThat(final @Nullable BuildTask buildTask) {
    return proxy(BuildTaskAssert.class, BuildTask.class, buildTask);
  }

  public BuildResultAssert assertThat(final @Nullable BuildResult buildResult) {
    return proxy(BuildResultAssert.class, BuildResult.class, buildResult);
  }
}

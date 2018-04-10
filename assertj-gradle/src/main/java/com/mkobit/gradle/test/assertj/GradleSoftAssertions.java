package com.mkobit.gradle.test.assertj;

import com.mkobit.gradle.test.assertj.api.artifacts.ConfigurationAssert;
import com.mkobit.gradle.test.assertj.testkit.BuildResultAssert;
import com.mkobit.gradle.test.assertj.testkit.BuildTaskAssert;
import org.assertj.core.api.SoftAssertions;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.gradle.api.artifacts.Configuration;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;

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

  public ConfigurationAssert assertThat(final @Nullable Configuration configuration) {
    return proxy(ConfigurationAssert.class, Configuration.class, configuration);
  }
}

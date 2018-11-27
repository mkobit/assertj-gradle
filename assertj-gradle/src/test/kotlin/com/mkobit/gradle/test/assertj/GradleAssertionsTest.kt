package com.mkobit.gradle.test.assertj

import com.mkobit.gradle.test.assertj.GradleAssertions.assertThat
import com.mkobit.gradle.test.assertj.gradleapi.artifacts.ConfigurationAssert
import com.mkobit.gradle.test.assertj.testkit.BuildResultAssert
import com.mkobit.gradle.test.assertj.testkit.BuildTaskAssert
import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions
import org.gradle.api.artifacts.Configuration
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.BuildTask
import org.junit.jupiter.api.Test

internal class GradleAssertionsTest {
  @Test
  internal fun `can use static factory method for BuildTaskAssert`() {
    val mockBuildTask: BuildTask = mock()
    Assertions.assertThatCode { assertThat(mockBuildTask) }.doesNotThrowAnyException()
    Assertions.assertThat(assertThat(mockBuildTask)).isExactlyInstanceOf(BuildTaskAssert::class.java)
  }

  @Test
  internal fun `can use static factory method for BuildResultAssert`() {
    val mockBuildResult: BuildResult = mock()
    Assertions.assertThatCode { assertThat(mockBuildResult) }.doesNotThrowAnyException()
    Assertions.assertThat(assertThat(mockBuildResult)).isExactlyInstanceOf(BuildResultAssert::class.java)
  }

  @Test
  internal fun `can use static factory method for ConfigurationAssert`() {
    val mockConfiguration: Configuration = mock()
    Assertions.assertThatCode { assertThat(mockConfiguration) }.doesNotThrowAnyException()
    Assertions.assertThat(assertThat(mockConfiguration)).isExactlyInstanceOf(ConfigurationAssert::class.java)
  }
}

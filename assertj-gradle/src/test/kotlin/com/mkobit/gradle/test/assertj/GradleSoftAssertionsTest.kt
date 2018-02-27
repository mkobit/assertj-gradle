package com.mkobit.gradle.test.assertj

import com.nhaarman.mockito_kotlin.mock
import org.assertj.core.api.Assertions
import org.gradle.api.artifacts.Configuration
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.BuildTask
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled("Fails on AssertJ 3.9.1")
internal class GradleSoftAssertionsTest {
  @Test
  internal fun `can use soft assertions with BuildTask`() {
    val mockBuildTask: BuildTask = mock()

    Assertions.assertThatCode {
      GradleSoftAssertions().apply { assertThat(mockBuildTask).isNotNull }.assertAll()
    }.doesNotThrowAnyException()
    Assertions.assertThatThrownBy {
      GradleSoftAssertions().apply { assertThat(mockBuildTask).isNull() }.assertAll()
    }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `can use soft assertions with BuildResult`() {
    val mockBuildResult: BuildResult = mock()

    Assertions.assertThatCode {
      GradleSoftAssertions().apply { assertThat(mockBuildResult).isNotNull }
    }.doesNotThrowAnyException()
    Assertions.assertThatThrownBy {
      GradleSoftAssertions().apply { assertThat(mockBuildResult).isNull() }.assertAll()
    }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `can use soft assertions with Configuration`() {
    val mockConfiguration: Configuration = mock()
    Assertions.assertThatCode {
      GradleSoftAssertions().apply { assertThat(mockConfiguration).isNotNull }
    }.doesNotThrowAnyException()
    Assertions.assertThatThrownBy {
      GradleSoftAssertions().apply { assertThat(mockConfiguration).isNull() }.assertAll()
    }.isInstanceOf(AssertionError::class.java)
  }
}

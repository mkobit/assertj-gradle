package com.mkobit.gradle.test.assertj.testkit

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testsupport.assertNoExceptionThrownBy
import testsupport.assertThatAssertionErrorThrownBy
import java.util.function.Consumer
import java.util.regex.Pattern

internal class BuildTaskAssertTest {
  companion object {
    private val TEST_PATH = ":taskName"
  }

  private lateinit var buildTaskAssert: BuildTaskAssert

  private lateinit var mockBuildTask: BuildTask

  @BeforeEach
  internal fun setUp() {
    mockBuildTask = mock {
      on { path } doReturn TEST_PATH
    }
    buildTaskAssert = BuildTaskAssert(mockBuildTask)
  }

  @Test
  internal fun `null constructor argument`() {
    val buildTaskAssert = BuildTaskAssert(null)
    assertNoExceptionThrownBy { buildTaskAssert.isNull() }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isNotNull }
  }

  @Test
  internal fun `FAILED task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.FAILED)

    assertNoExceptionThrownBy { buildTaskAssert.hasTaskOutcome(TaskOutcome.FAILED) }

    assertNoExceptionThrownBy { buildTaskAssert.isFailed }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isFromCache }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isNoSource }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSkipped }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSuccess }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isUpToDate }
  }

  @Test
  internal fun `FROM_CACHE task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.FROM_CACHE)

    assertNoExceptionThrownBy { buildTaskAssert.hasTaskOutcome(TaskOutcome.FROM_CACHE) }

    assertThatAssertionErrorThrownBy { buildTaskAssert.isFailed }
    assertNoExceptionThrownBy { buildTaskAssert.isFromCache }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isNoSource }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSkipped }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSuccess }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isUpToDate }
  }

  @Test
  internal fun `NO_SOURCE task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.NO_SOURCE)

    assertNoExceptionThrownBy { buildTaskAssert.hasTaskOutcome(TaskOutcome.NO_SOURCE) }

    assertThatAssertionErrorThrownBy { buildTaskAssert.isFailed }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isFromCache }
    assertNoExceptionThrownBy { buildTaskAssert.isNoSource }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSkipped }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSuccess }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isUpToDate }
  }

  @Test
  internal fun `SKIPPED task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.SKIPPED)

    assertNoExceptionThrownBy { buildTaskAssert.hasTaskOutcome(TaskOutcome.SKIPPED) }

    assertThatAssertionErrorThrownBy { buildTaskAssert.isFromCache }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isNoSource }
    assertNoExceptionThrownBy { buildTaskAssert.isSkipped }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSuccess }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isUpToDate }
  }

  @Test
  internal fun `SUCCESS task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.SUCCESS)

    assertNoExceptionThrownBy { buildTaskAssert.hasTaskOutcome(TaskOutcome.SUCCESS) }

    assertThatAssertionErrorThrownBy { buildTaskAssert.isFailed }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isFromCache }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isNoSource }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSkipped }
    assertNoExceptionThrownBy { buildTaskAssert.isSuccess }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isUpToDate }
  }

  @Test
  internal fun `UP_TO_DATE task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.UP_TO_DATE)

    assertNoExceptionThrownBy { buildTaskAssert.hasTaskOutcome(TaskOutcome.UP_TO_DATE) }

    assertThatAssertionErrorThrownBy { buildTaskAssert.isFailed }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isFromCache }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isNoSource }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSkipped }
    assertThatAssertionErrorThrownBy { buildTaskAssert.isSuccess }
    assertNoExceptionThrownBy { buildTaskAssert.isUpToDate }
  }

  @Test
  internal fun `path equals`() {
    assertNoExceptionThrownBy { buildTaskAssert.pathIsEqualTo(TEST_PATH) }
    assertThatAssertionErrorThrownBy { buildTaskAssert.pathIsEqualTo(":notThisPath") }
  }

  @Test
  internal fun `path begins with`() {
    assertNoExceptionThrownBy { buildTaskAssert.pathStartsWith(":task") }
    assertThatAssertionErrorThrownBy { buildTaskAssert.pathStartsWith(":nope") }
  }

  @Test
  internal fun `path ends with`() {
    assertNoExceptionThrownBy { buildTaskAssert.pathEndsWith("Name") }
    assertThatAssertionErrorThrownBy { buildTaskAssert.pathEndsWith("Nope") }
  }

  @Test
  internal fun `path contains`() {
    assertNoExceptionThrownBy { buildTaskAssert.pathContains("skNa") }
    assertThatAssertionErrorThrownBy { buildTaskAssert.pathContains("nope") }
  }

  @Test
  internal fun `path matches`() {
    assertNoExceptionThrownBy { buildTaskAssert.pathMatches(Pattern.compile("^:task.*$")) }
    assertThatAssertionErrorThrownBy { buildTaskAssert.pathMatches(Pattern.compile("^nope.*")) }
  }

  @Test
  internal fun `path does not match`() {
    assertNoExceptionThrownBy { buildTaskAssert.pathDoesNotMatch(Pattern.compile("^nope.*")) }
    assertThatAssertionErrorThrownBy { buildTaskAssert.pathDoesNotMatch(Pattern.compile("^:task.*$")) }
  }

  @Test
  internal fun `path satisfies`() {
    val mockConsumer: Consumer<String?> = mock()
    assertNoExceptionThrownBy { buildTaskAssert.pathSatisfies(mockConsumer) }
    verify(mockConsumer, times(1)).accept(TEST_PATH)
  }
}

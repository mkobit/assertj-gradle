package com.mkobit.gradle.test.assertj.testkit

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    assertThatCode { buildTaskAssert.isNull() }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.isNotNull }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `FAILED task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.FAILED)

    assertThatCode { buildTaskAssert.hasTaskOutcome(TaskOutcome.FAILED) }.doesNotThrowAnyException()

    assertThatCode { buildTaskAssert.isFailed }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.isFromCache }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isNoSource }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isSkipped }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isSuccess }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isUpToDate }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `FROM_CACHE task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.FROM_CACHE)

    assertThatCode { buildTaskAssert.hasTaskOutcome(TaskOutcome.FROM_CACHE) }.doesNotThrowAnyException()

    assertThatThrownBy { buildTaskAssert.isFailed }.isInstanceOf(AssertionError::class.java)
    assertThatCode { buildTaskAssert.isFromCache }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.isNoSource }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isSkipped }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isSuccess }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isUpToDate }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `NO_SOURCE task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.NO_SOURCE)

    assertThatCode { buildTaskAssert.hasTaskOutcome(TaskOutcome.NO_SOURCE) }.doesNotThrowAnyException()

    assertThatThrownBy { buildTaskAssert.isFailed }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isFromCache }.isInstanceOf(AssertionError::class.java)
    assertThatCode { buildTaskAssert.isNoSource }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.isSkipped }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isSuccess }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isUpToDate }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `SKIPPED task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.SKIPPED)

    assertThatCode { buildTaskAssert.hasTaskOutcome(TaskOutcome.SKIPPED) }.doesNotThrowAnyException()

    assertThatThrownBy { buildTaskAssert.isFromCache }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isNoSource }.isInstanceOf(AssertionError::class.java)
    assertThatCode { buildTaskAssert.isSkipped }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.isSuccess }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isUpToDate }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `SUCCESS task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.SUCCESS)

    assertThatCode { buildTaskAssert.hasTaskOutcome(TaskOutcome.SUCCESS) }.doesNotThrowAnyException()

    assertThatThrownBy { buildTaskAssert.isFailed }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isFromCache }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isNoSource }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isSkipped }.isInstanceOf(AssertionError::class.java)
    assertThatCode { buildTaskAssert.isSuccess }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.isUpToDate }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `UP_TO_DATE task outcome`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.UP_TO_DATE)

    assertThatCode { buildTaskAssert.hasTaskOutcome(TaskOutcome.UP_TO_DATE) }.doesNotThrowAnyException()

    assertThatThrownBy { buildTaskAssert.isFailed }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isFromCache }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isNoSource }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isSkipped }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { buildTaskAssert.isSuccess }.isInstanceOf(AssertionError::class.java)
    assertThatCode { buildTaskAssert.isUpToDate }.doesNotThrowAnyException()
  }

  @Test
  internal fun `path equals`() {
    assertThatCode { buildTaskAssert.pathIsEqualTo(TEST_PATH) }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.pathIsEqualTo(":notThisPath") }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `path begins with`() {
    assertThatCode { buildTaskAssert.pathStartsWith(":task") }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.pathStartsWith(":nope") }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `path ends with`() {
    assertThatCode { buildTaskAssert.pathEndsWith("Name") }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.pathEndsWith("Nope") }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `path contains`() {
    assertThatCode { buildTaskAssert.pathContains("skNa") }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.pathContains("nope") }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `path matches`() {
    assertThatCode { buildTaskAssert.pathMatches(Pattern.compile("^:task.*$")) }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.pathMatches(Pattern.compile("^nope.*")) }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `path does not match`() {
    assertThatCode { buildTaskAssert.pathDoesNotMatch(Pattern.compile("^nope.*")) }.doesNotThrowAnyException()
    assertThatThrownBy { buildTaskAssert.pathDoesNotMatch(Pattern.compile("^:task.*$")) }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `path satisfies`() {
    val mockConsumer: Consumer<String?> = mock()
    assertThatCode { buildTaskAssert.pathSatisfies(mockConsumer) }.doesNotThrowAnyException()
    verify(mockConsumer, times(1)).accept(TEST_PATH)
  }
}

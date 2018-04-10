package com.mkobit.gradle.test.assertk.testkit.runner

import assertk.Assert
import assertk.assert
import assertk.assertions.isSameAs
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

private const val TASK_PATH = ":tasky"

internal class BuildTaskTest {
  private lateinit var mockBuildTask: BuildTask

  @BeforeEach
  internal fun setUp() {
    mockBuildTask = mock {
      on { path } doReturn TASK_PATH
    }
  }

  @Test
  internal fun `hasOutcome assertion`() {
    assertionTestForOutcome(TaskOutcome.SUCCESS, TaskOutcome.SUCCESS) { it.hasOutcome(TaskOutcome.SUCCESS) }
    assertionTestForOutcome(TaskOutcome.SUCCESS, TaskOutcome.FAILED) { it.hasOutcome(TaskOutcome.SUCCESS) }
  }

  @Test
  internal fun `outcome builder`() {
    whenever(mockBuildTask.outcome).thenReturn(TaskOutcome.SUCCESS)

    assert(mockBuildTask).outcome().isSameAs(TaskOutcome.SUCCESS)
  }

  @ParameterizedTest
  @EnumSource(TaskOutcome::class)
  internal fun `isSuccess assertion`(outcome: TaskOutcome) {
    assertionTestForOutcome(TaskOutcome.SUCCESS, outcome, Assert<BuildTask>::isSuccess)
  }

  @ParameterizedTest
  @EnumSource(TaskOutcome::class)
  internal fun `isFailed assertion`(outcome: TaskOutcome) {
    assertionTestForOutcome(TaskOutcome.FAILED, outcome, Assert<BuildTask>::isFailed)
  }

  @ParameterizedTest
  @EnumSource(TaskOutcome::class)
  internal fun `isUpToDate assertion`(outcome: TaskOutcome) {
    assertionTestForOutcome(TaskOutcome.UP_TO_DATE, outcome, Assert<BuildTask>::isUpToDate)
  }

  @ParameterizedTest
  @EnumSource(TaskOutcome::class)
  internal fun `isSkipped assertion`(outcome: TaskOutcome) {
    assertionTestForOutcome(TaskOutcome.SKIPPED, outcome, Assert<BuildTask>::isSkipped)
  }

  @ParameterizedTest
  @EnumSource(TaskOutcome::class)
  internal fun `isFromCache assertion`(outcome: TaskOutcome) {
    assertionTestForOutcome(TaskOutcome.FROM_CACHE, outcome, Assert<BuildTask>::isFromCache)
  }

  @ParameterizedTest
  @EnumSource(TaskOutcome::class)
  internal fun `isNoSource assertion`(outcome: TaskOutcome) {
    assertionTestForOutcome(TaskOutcome.NO_SOURCE, outcome, Assert<BuildTask>::isNoSource)
  }

  private fun assertionTestForOutcome(
    successOutcome: TaskOutcome,
    outcome: TaskOutcome,
    assertionMethod: (Assert<BuildTask>) -> Unit
  ) {
    whenever(mockBuildTask.outcome).thenReturn(outcome)
    when (outcome) {
      successOutcome -> {
        assertThatCode {
          assertionMethod(assert(mockBuildTask))
        }.doesNotThrowAnyException()
      }
      else -> {
        assertThatThrownBy {
          assertionMethod(assert(mockBuildTask))
        }.isInstanceOf(AssertionError::class.java)
          .hasMessage("""expected task at path <"$TASK_PATH"> to have outcome <$successOutcome> but was <$outcome>""")
      }
    }
  }

  @Test
  internal fun `path assertion builder`() {
    assert(mockBuildTask).path().isSameAs(TASK_PATH)
  }

  @Test
  fun `hasPath assertion`() {
    val taskPath = ":pizza"
    whenever(mockBuildTask.path).thenReturn(":pizza", ":party")
    assertThatCode {
      assert(mockBuildTask).hasPath(taskPath)
    }.doesNotThrowAnyException()

    assertThatThrownBy {
      assert(mockBuildTask).hasPath(taskPath)
    }.isInstanceOf(AssertionError::class.java)
      .hasMessage("""expected task path <":pizza"> but was <":party">""")
  }
}

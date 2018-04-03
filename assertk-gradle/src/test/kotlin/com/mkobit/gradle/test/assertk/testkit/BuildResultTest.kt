package com.mkobit.gradle.test.assertk.testkit

import assertk.assert
import assertk.assertions.isNull
import assertk.assertions.isSameAs
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BuildResultTest {
  private lateinit var mockBuildResult: BuildResult
  private lateinit var mockBuildTask: BuildTask

  @BeforeEach
  internal fun setUp() {
    mockBuildResult = mock()
    mockBuildTask = mock()
  }

  @Test
  internal fun `task from task path can be null`() {
    val taskPath = ":path"
    assert(mockBuildResult).task(taskPath).isNull()
  }

  @Test
  internal fun `task at task path`() {
    val taskPath = ":path"
    whenever(mockBuildResult.task(taskPath)).thenReturn(mockBuildTask)

    assert(mockBuildResult).task(taskPath).isSameAs(mockBuildTask)

    verify(mockBuildResult).task(taskPath)
    verifyNoMoreInteractions(mockBuildResult)
  }

  @Test
  internal fun `task paths with outcome`() {
    val tasks = listOf(":pizza", ":party")
    whenever(mockBuildResult.taskPaths(TaskOutcome.SUCCESS)).thenReturn(tasks)

    assert(mockBuildResult).taskPaths(TaskOutcome.SUCCESS).isSameAs(tasks)

    verify(mockBuildResult).taskPaths(TaskOutcome.SUCCESS)
    verifyNoMoreInteractions(mockBuildResult)
  }

  @Test
  internal fun `tasks with outcome`() {
    val tasks = listOf(mockBuildTask)
    whenever(mockBuildResult.tasks(TaskOutcome.SUCCESS)).thenReturn(tasks)

    assert(mockBuildResult).tasks(TaskOutcome.SUCCESS).isSameAs(tasks)

    verify(mockBuildResult).tasks(TaskOutcome.SUCCESS)
    verifyNoMoreInteractions(mockBuildResult)
  }

  @Test
  internal fun `build output`() {
    val output = "pineapple pizza is factually bad"
    whenever(mockBuildResult.output).thenReturn(output)

    assert(mockBuildResult).output().isSameAs(output)

    verify(mockBuildResult).output
    verifyNoMoreInteractions(mockBuildResult)
  }
}

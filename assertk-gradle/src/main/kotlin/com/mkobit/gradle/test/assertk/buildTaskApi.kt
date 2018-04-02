package com.mkobit.gradle.test.assertk

import assertk.Assert
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome

/**
 * Asserts that the [BuildTask.getOutcome] is [outcome].
 */
fun <T : BuildTask> Assert<T>.hasOutcome(outcome: TaskOutcome) {
  if (actual.outcome != outcome) {
    expected("task at path ${actual.path}: ${show(outcome)} but was ${show(actual.outcome)}")
  }
}

/**
 * Produces an [Assert] on the [BuildTask.getOutcome].
 */
fun <T : BuildTask> Assert<T>.outcome(): Assert<TaskOutcome> = prop("outcome", BuildTask::getOutcome)

/**
 * Asserts that the [BuildTask.getOutcome] is [TaskOutcome.SUCCESS].
 */
fun <T : BuildTask> Assert<T>.isSuccess(): Unit = hasOutcome(TaskOutcome.SUCCESS)

/**
 * Asserts that the [BuildTask.getOutcome] is [TaskOutcome.FAILED].
 */
fun <T : BuildTask> Assert<T>.isFailed(): Unit = hasOutcome(TaskOutcome.FAILED)

/**
 * Asserts that the [BuildTask.getOutcome] is [TaskOutcome.UP_TO_DATE].
 */
fun <T : BuildTask> Assert<T>.isUpToDate(): Unit = hasOutcome(TaskOutcome.UP_TO_DATE)

/**
 * Asserts that the [BuildTask.getOutcome] is [TaskOutcome.SKIPPED].
 */
fun <T : BuildTask> Assert<T>.isSkipped(): Unit = hasOutcome(TaskOutcome.SKIPPED)

/**
 * Asserts that the [BuildTask.getOutcome] is [TaskOutcome.FROM_CACHE].
 */
fun <T : BuildTask> Assert<T>.isFromCache(): Unit = hasOutcome(TaskOutcome.FROM_CACHE)

/**
 * Asserts that the [BuildTask.getOutcome] is [TaskOutcome.NO_SOURCE].
 */
fun <T : BuildTask> Assert<T>.isNoSource(): Unit = hasOutcome(TaskOutcome.NO_SOURCE)

/**
 * Produces an [Assert] on the [BuildTask.getPath].
 */
fun <T : BuildTask> Assert<T>.path(): Assert<String> = prop("task path", BuildTask::getPath)

/**
 * Asserts that the [BuildTask.getPath] is equal to the provided [taskPath]
 */
fun <T : BuildTask> Assert<T>.hasPath(taskPath: String) {
  if (actual.path != taskPath) {
    expected("task path ${show(taskPath)} but was ${show(actual.path)}")
  }
}

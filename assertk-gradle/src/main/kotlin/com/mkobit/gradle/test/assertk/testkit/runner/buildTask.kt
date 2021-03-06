package com.mkobit.gradle.test.assertk.testkit.runner

import assertk.Assert
import assertk.assertions.prop
import assertk.assertions.support.expected
import assertk.assertions.support.show
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome

/**
 * Asserts that the outcome is equal to [outcome].
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assert<T>.hasOutcome(outcome: TaskOutcome) {
  if (actual.outcome != outcome) {
    expected("task at path ${show(actual.path)} to have outcome ${show(outcome)} but was ${show(actual.outcome)}")
  }
}

/**
 * Produces an [Assert] on the outcome.
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assert<T>.outcome(): Assert<TaskOutcome> = prop("outcome", BuildTask::getOutcome)

/**
 * Asserts that the outcome is `SUCCESS`.
 * @see BuildTask.getOutcome
 * @see TaskOutcome.SUCCESS
 */
fun <T : BuildTask> Assert<T>.isSuccess(): Unit = hasOutcome(TaskOutcome.SUCCESS)

/**
 * Asserts that the outcome is `FAILED`.
 * @see BuildTask.getOutcome
 * @see TaskOutcome.FAILED
 */
fun <T : BuildTask> Assert<T>.isFailed(): Unit = hasOutcome(TaskOutcome.FAILED)

/**
 * Asserts that the outcome is `UP_TO_DATE`.
 * @see BuildTask.getOutcome
 * @see TaskOutcome.UP_TO_DATE
 */
fun <T : BuildTask> Assert<T>.isUpToDate(): Unit = hasOutcome(TaskOutcome.UP_TO_DATE)

/**
 * Asserts that the outcome is `SKIPPED`.
 * @see BuildTask.getOutcome
 * @see TaskOutcome.SKIPPED
 */
fun <T : BuildTask> Assert<T>.isSkipped(): Unit = hasOutcome(TaskOutcome.SKIPPED)

/**
 * Asserts that the outcome is `FROM_CACHE`.
 * @see BuildTask.getOutcome
 * @see TaskOutcome.FROM_CACHE
 */
fun <T : BuildTask> Assert<T>.isFromCache(): Unit = hasOutcome(TaskOutcome.FROM_CACHE)

/**
 * Asserts that the outcome is `NO_SOURCE`.
 * @see BuildTask.getOutcome
 * @see TaskOutcome.NO_SOURCE
 */
fun <T : BuildTask> Assert<T>.isNoSource(): Unit = hasOutcome(TaskOutcome.NO_SOURCE)

/**
 * Produces an [Assert] on the task path.
 * @see BuildTask.getPath
 */
fun <T : BuildTask> Assert<T>.path(): Assert<String> = prop("task path", BuildTask::getPath)

/**
 * Asserts that the task path is equal to the provided [taskPath]
 * @see BuildTask.getPath
 */
fun <T : BuildTask> Assert<T>.hasPath(taskPath: String) {
  if (actual.path != taskPath) {
    expected("task path ${show(taskPath)} but was ${show(actual.path)}")
  }
}

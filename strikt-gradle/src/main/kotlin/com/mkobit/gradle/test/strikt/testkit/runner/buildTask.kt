package com.mkobit.gradle.test.strikt.testkit.runner

import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome
import strikt.api.Assertion

/**
 * Asserts that the outcome is equal to [outcome].
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assertion.Builder<T>.hasOutcome(outcome: TaskOutcome) =
  assert("outcome is %s", outcome) {
    if (it.outcome == outcome) {
      pass()
    } else {
      fail(it.outcome)
    }
  }

/**
 * Maps an assertion to the task outcome.
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assertion.Builder<T>.outcome(): Assertion.Builder<TaskOutcome> =
  get("outcome", BuildTask::getOutcome)

/**
 * Asserts that the outcome is [TaskOutcome.SUCCESS].
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assertion.Builder<T>.isSuccess() =
  hasOutcome(TaskOutcome.SUCCESS)

/**
 * Asserts that the outcome is [TaskOutcome.FAILED].
 */
fun <T : BuildTask> Assertion.Builder<T>.isFailed() =
  hasOutcome(TaskOutcome.FAILED)

/**
 * Asserts that the outcome is [TaskOutcome.UP_TO_DATE].
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assertion.Builder<T>.isUpToDate() =
  hasOutcome(TaskOutcome.UP_TO_DATE)

/**
 * Asserts that the outcome is [TaskOutcome.SKIPPED].
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assertion.Builder<T>.isSkipped() =
  hasOutcome(TaskOutcome.SKIPPED)

/**
 * Asserts that the outcome is [TaskOutcome.FROM_CACHE].
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assertion.Builder<T>.isFromCache() =
  hasOutcome(TaskOutcome.FROM_CACHE)

/**
 * Asserts that the outcome is [TaskOutcome.NO_SOURCE].
 * @see BuildTask.getOutcome
 */
fun <T : BuildTask> Assertion.Builder<T>.isNoSource() =
  hasOutcome(TaskOutcome.NO_SOURCE)

/**
 * Maps an assertion to the task path.
 * @see BuildTask.getPath
 */
val <T : BuildTask> Assertion.Builder<T>.path: Assertion.Builder<String>
  get() = get("task path", BuildTask::getPath)

/**
 * Asserts that the task path is equal to the provided [taskPath]
 * @see BuildTask.getPath
 */
fun <T : BuildTask> Assertion.Builder<T>.hasPath(taskPath: String) =
  assert("task path is %s", taskPath) {
    if (it.path == taskPath) {
      pass()
    } else {
      fail(it.path)
    }
  }

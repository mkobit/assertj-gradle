package com.mkobit.gradle.test.assertk.testkit

import assertk.Assert
import assertk.assertions.prop
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome

/**
 * Produces an [Assert] for the task at [taskPath].
 * @see BuildResult.task
 */
fun <T : BuildResult> Assert<T>.task(taskPath: String): Assert<BuildTask?> =
    prop("task path") { it.task(taskPath) }

/**
 * Produces an [Assert] for all task paths with the provided [outcome].
 * @see BuildResult.taskPaths
 */
fun <T : BuildResult> Assert<T>.taskPaths(outcome: TaskOutcome): Assert<List<String>> =
    prop("task paths with outcome $outcome") { it.taskPaths(outcome) }

/**
 * Produces an [Assert] for all tasks.
 * @see BuildResult.getTasks
 */
fun <T : BuildResult> Assert<T>.tasks(): Assert<List<BuildTask>> = prop("all tasks", BuildResult::getTasks)

/**
 * Produces an [Assert] for the tasks with the
 * @see BuildResult
 */
fun <T : BuildResult> Assert<T>.tasks(outcome: TaskOutcome): Assert<List<BuildTask>> =
    prop("tasks with outcome $outcome") { it.tasks(outcome) }

/**
 * Produces an [Assert] for the
 * @see BuildResult
 */
fun <T : BuildResult> Assert<T>.output(): Assert<String> =
    prop("output", BuildResult::getOutput)

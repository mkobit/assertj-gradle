package com.mkobit.gradle.test.assertk.gradleapi

import assertk.Assert
import assertk.assertions.prop
import org.gradle.api.Task

/**
 * Produces an [Assert] for the description.
 * @see [Task.getDescription]
 */
fun <T : Task> Assert<T>.description(): Assert<String?> = prop(Task::getDescription)

/**
 * Produces an [Assert] for if the task is enabled.
 * @see [Task.getEnabled]
 */
fun <T : Task> Assert<T>.enabled(): Assert<Boolean> = prop(Task::getEnabled)

/**
 * Produces an [Assert] for the description.
 * @see [Task.getGroup]
 */
fun <T : Task> Assert<T>.group(): Assert<String?> = prop(Task::getGroup)

/**
 * Produces an [Assert] for the task NAME.
 * @see [Task.getPath]
 */
fun <T : Task> Assert<T>.name(): Assert<String> = prop(Task::getName)

/**
 * Produces an [Assert] for the task path.
 * @see [Task.getPath]
 */
fun <T : Task> Assert<T>.path(): Assert<String> = prop(Task::getPath)


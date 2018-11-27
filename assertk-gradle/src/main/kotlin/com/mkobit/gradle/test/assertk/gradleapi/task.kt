package com.mkobit.gradle.test.assertk.api

import assertk.Assert
import assertk.assertions.prop
import org.gradle.api.Task

/**
 * Produces an [Assert] for the description.
 * @see [Task.getDescription]
 */
fun Assert<Task>.description(): Assert<String?> = prop(Task::getDescription)

/**
 * Produces an [Assert] for the description.
 * @see [Task.getGroup]
 */
fun Assert<Task>.group(): Assert<String?> = prop(Task::getGroup)

/**
 * Produces an [Assert] for if the task is enabled.
 * @see [Task.getEnabled]
 */
fun Assert<Task>.enabled(): Assert<Boolean> = prop(Task::getEnabled)

/**
 * Produces an [Assert] for the task path.
 * @see [Task.getPath]
 */
fun Assert<Task>.path(): Assert<String> = prop(Task::getPath)


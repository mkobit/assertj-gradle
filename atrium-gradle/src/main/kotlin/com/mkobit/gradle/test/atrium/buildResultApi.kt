package com.mkobit.gradle.test.atrium

import ch.tutteli.atrium.assertions._returnValueOf
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlantNullable
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome

val Assert<BuildResult>.output: Assert<String> get() =
  _returnValueOf(this, subject::getOutput)

fun Assert<BuildResult>.tasks(taskOutcome: TaskOutcome): Assert<List<BuildTask>> =
    _returnValueOf(this, subject::tasks, taskOutcome)

fun Assert<BuildResult>.taskPaths(taskOutcome: TaskOutcome): Assert<List<String>> =
    _returnValueOf(this, subject::taskPaths, taskOutcome)

fun Assert<BuildResult>.task(taskPath: String): AssertionPlantNullable<BuildTask?> =
    _returnValueOf(this, subject::task, taskPath)

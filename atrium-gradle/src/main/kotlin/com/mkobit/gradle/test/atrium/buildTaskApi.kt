package com.mkobit.gradle.test.atrium

import ch.tutteli.atrium.assertions._returnValueOf
import ch.tutteli.atrium.creating.Assert
import org.gradle.testkit.runner.BuildTask
import org.gradle.testkit.runner.TaskOutcome

val Assert<BuildTask>.path: Assert<String> get() =
    _returnValueOf(this, subject::getPath)

val Assert<BuildTask>.outcome: Assert<TaskOutcome> get() =
  _returnValueOf(this, subject::getOutcome)

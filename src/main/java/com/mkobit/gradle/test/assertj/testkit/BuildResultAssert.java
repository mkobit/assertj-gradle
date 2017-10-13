package com.mkobit.gradle.test.assertj.testkit;

import org.assertj.core.api.AbstractAssert;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.TaskOutcome;

import javax.annotation.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Assertion methods for {@link BuildResult}.
 *
 * @see com.mkobit.gradle.test.assertj.GradleAssertions
 * @see com.mkobit.gradle.test.assertj.GradleSoftAssertions
 * @see BuildTaskAssert
 */
public class BuildResultAssert extends AbstractAssert<BuildResultAssert, BuildResult> {
  public BuildResultAssert(final @Nullable BuildResult actual) {
    super(actual, BuildResultAssert.class);
  }
  //  String getOutput();

  /**
   * Verifies that the {@link BuildResult#getOutput()} contains the provided sequence.
   *
   * @param sequence the sequence to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code sequence} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} output does not contain with the provided {@code sequence}
   */
  public BuildResultAssert outputContains(final CharSequence sequence) {
    isNotNull();
    Objects.requireNonNull(sequence, "sequence");

    if (!actual.getOutput().contains(sequence)) {
      failWithMessage("%nExpecting build result output:%n <%s>%nto contain sequence:%n <%s>",
                      actual.getOutput(),
                      sequence);
    }

    return this;
  }

  /**
   * Verifies that the {@link BuildResult#getOutput()} does not contain the provided sequence.
   *
   * @param sequence the sequence to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code sequence} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} output contains the provided {@code sequence}
   */
  public BuildResultAssert outputDoesNotContain(final CharSequence sequence) {
    isNotNull();
    Objects.requireNonNull(sequence, "sequence");

    if (actual.getOutput().contains(sequence)) {
      failWithMessage("%nExpecting build result output:%n <%s>%nto not contain sequence:%n <%s>",
                      actual.getOutput(),
                      sequence);
    }

    return this;
  }

  /**
   * Verifies that the {@link BuildResult#getOutput()} matches the provided pattern.
   *
   * @param pattern the pattern to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code pattern} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} output does not match with the provided {@code pattern}
   */
  public BuildResultAssert outputMatches(final Pattern pattern) {
    isNotNull();
    Objects.requireNonNull(pattern, "pattern");

    if (!pattern.matcher(actual.getOutput()).matches()) {
      failWithMessage("%nExpecting build output to match:%n <%s>%nbut was:%n <%s>", pattern, actual.getOutput());
    }

    return this;
  }

  /**
   * Verifies that the {@link BuildResult#getOutput()} does not match the provided pattern.
   *
   * @param pattern the pattern to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code pattern} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} output matches the provided {@code pattern}
   */
  public BuildResultAssert outputDoesNotMatch(final Pattern pattern) {
    isNotNull();
    Objects.requireNonNull(pattern, "pattern");

    if (pattern.matcher(actual.getOutput()).matches()) {
      failWithMessage("%nExpecting build output not to match:%n <%s>%nbut was:%n <%s>", pattern, actual.getOutput());
    }

    return this;
  }

  /**
   * Verifies that the {@link BuildResult#getOutput()} satisfies the provided requirements.
   *
   * @param requirements the requirements for the output
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @see AbstractAssert#satisfies(Consumer)
   */
  public BuildResultAssert outputSatisfies(final Consumer<String> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements,
                           "Consumer<String> of output expressing assertions requirements must not be null");
    requirements.accept(actual.getOutput());
    return this;
  }
  //  List<String> taskPaths(TaskOutcome var1);
  //  List<BuildTask> tasks(TaskOutcome var1);
  //  List<BuildTask> getTasks();

  //  @Nullable
  //  BuildTask task(String var1);

  /**
   * Verifies a task does not exist at the provided {@code path}
   *
   * @param path the task path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not have a task at the provided path
   * @see BuildResult#task(String)
   */
  public BuildResultAssert hasTaskAtPath(final CharSequence path) {
    isNotNull();
    Objects.requireNonNull(path, "path");

    final String taskPath = path.toString();
    final BuildTask buildTask = actual.task(taskPath);
    if (buildTask == null) {
      failWithMessage("%nExpecting build to have task at path:%n <%s>%nbut did not", taskPath);
    }
    return this;
  }

  /**
   * Verifies a task does not exist at the provided {@code path}
   *
   * @param path the task path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} has a task at the provided path
   * @see BuildResult#task(String)
   */
  public BuildResultAssert doesNotHaveTaskAtPath(final CharSequence path) {
    isNotNull();
    Objects.requireNonNull(path, "path");

    final String taskPath = path.toString();
    final BuildTask buildTask = actual.task(taskPath);
    if (buildTask != null) {
      failWithMessage("%nExpecting build to not have task at path:%n <%s>%nbut did", taskPath);
    }
    return this;
  }

  /**
   * Verifies a task exists at the provided {@code path} and that is satisfies the provided {@code requirements}.
   *
   * @param path the task path
   * @param requirements the requirements for the task at the provided path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not have a task at the provided {@code path}
   * @see BuildResult#task(String)
   */
  public BuildResultAssert hasTaskAtPathSatisfying(final CharSequence path,
                                                   final Consumer<? super BuildTask> requirements) {
    isNotNull();
    Objects.requireNonNull(path, "path");
    Objects.requireNonNull(requirements, "requirements");

    final String taskPath = path.toString();
    final BuildTask buildTask = actual.task(taskPath);
    if (buildTask == null) {
      failWithMessage(
          "%nExpecting build to have task at path:%n <%s>%nto check requirements against but did not",
          taskPath);
    }
    requirements.accept(buildTask);
    return this;
  }

  /**
   * Verifies that tasks with the provided {@code outcome} satisfy the provided {@code requirements}.
   *
   * @param outcome the {@link TaskOutcome} to retrieve the tasks for
   * @param requirements the requirements for the tasks with the provided outcome
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code outcome} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @see BuildResult#tasks(TaskOutcome)
   */
  public BuildResultAssert tasksWithOutcomeSatisfy(
      final TaskOutcome outcome,
      final Consumer<List<BuildTask>> requirements
  ) {
    isNotNull();
    Objects.requireNonNull(outcome, "outcome");
    Objects.requireNonNull(requirements, "requirements");

    requirements.accept(actual.tasks(outcome));
    return this;
  }

  /**
   * Verifies that task paths with the provided {@code outcome} satisfy the provided {@code requirements}.
   *
   * @param outcome the outcome to retrieve the task paths for
   * @param requirements the requirements for the task paths with the provided outcome
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code outcome} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @see BuildResult#taskPaths(TaskOutcome)
   */
  public BuildResultAssert taskPathsWithOutcomeSatisfy(
      final TaskOutcome outcome,
      final Consumer<List<String>> requirements
  ) {
    isNotNull();
    Objects.requireNonNull(outcome, "outcome must not be null");
    Objects.requireNonNull(requirements, "requirements must not be null");

    requirements.accept(actual.taskPaths(outcome));
    return this;
  }

  /**
   * Verifies that the tasks in this build result satisfy the provided {@code requirements}.
   *
   * @param requirements the requirements for the tasks
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   */
  public BuildResultAssert tasksSatisfy(final Consumer<List<BuildTask>> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getTasks());
    return this;
  }

  /**
   * Verifies that a task exists at the provided {@code path} with outcome {@link TaskOutcome#FAILED}.
   *
   * @param path the task path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the task at the provided {@code path} does not exist
   * @throws AssertionError if the task at the provided path does not have outcome {@link TaskOutcome#FAILED}
   */
  public BuildResultAssert hasTaskFailedAtPath(final CharSequence path) {
    return hasTaskAtPathWithOutcome(path, TaskOutcome.FAILED);
  }

  /**
   * Verifies that a task exists at the provided {@code path} with outcome {@link TaskOutcome#FROM_CACHE}.
   *
   * @param path the task path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the task at the provided {@code path} does not exist
   * @throws AssertionError if the task at the provided path does not have outcome {@link TaskOutcome#FROM_CACHE}
   */
  public BuildResultAssert hasTaskFromCacheAtPath(final CharSequence path) {
    return hasTaskAtPathWithOutcome(path, TaskOutcome.FROM_CACHE);
  }

  /**
   * Verifies that a task exists at the provided {@code path} with outcome {@link TaskOutcome#NO_SOURCE}.
   *
   * @param path the task path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the task at the provided {@code path} does not exist
   * @throws AssertionError if the task at the provided path does not have outcome {@link TaskOutcome#NO_SOURCE}
   */
  public BuildResultAssert hasTaskNoSourceAtPath(final CharSequence path) {
    return hasTaskAtPathWithOutcome(path, TaskOutcome.NO_SOURCE);
  }

  /**
   * Verifies that a task exists at the provided {@code path} with outcome {@link TaskOutcome#SKIPPED}.
   *
   * @param path the task path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the task at the provided {@code path} does not exist
   * @throws AssertionError if the task at the provided path does not have outcome {@link TaskOutcome#SKIPPED}
   */
  public BuildResultAssert hasTaskSkippedAtPath(final CharSequence path) {
    return hasTaskAtPathWithOutcome(path, TaskOutcome.SKIPPED);
  }

  /**
   * Verifies that a task exists at the provided {@code path} with outcome {@link TaskOutcome#SUCCESS}.
   *
   * @param path the task path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the task at the provided {@code path} does not exist
   * @throws AssertionError if the task at the provided path does not have outcome {@link TaskOutcome#SUCCESS}
   */
  public BuildResultAssert hasTaskSuccessAtPath(final CharSequence path) {
    return hasTaskAtPathWithOutcome(path, TaskOutcome.SUCCESS);
  }

  /**
   * Verifies that a task exists at the provided {@code path} with outcome {@link TaskOutcome#UP_TO_DATE}.
   *
   * @param path the task path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the task at the provided {@code path} does not exist
   * @throws AssertionError if the task at the provided path does not have outcome {@link TaskOutcome#UP_TO_DATE}
   */
  public BuildResultAssert hasTaskUpToDateAtPath(final CharSequence path) {
    return hasTaskAtPathWithOutcome(path, TaskOutcome.UP_TO_DATE);
  }

  /**
   * Verifies that a task exists at the provided {@code path} with the provided {@code outcome}.
   *
   * @param path the task path
   * @param outcome the expected outcome
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws NullPointerException if the provided {@code outcome} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the task at the provided {@code path} does not exist
   * @throws AssertionError if the task at the provided path does not have the provided {@code outcome}
   */
  @SuppressWarnings("ConstantConditions")
  public BuildResultAssert hasTaskAtPathWithOutcome(final CharSequence path,
                                                    final TaskOutcome outcome) {
    isNotNull();
    Objects.requireNonNull(outcome, "outcome must not be null");
    final String taskPath = path.toString();
    final BuildTask buildTask = actual.task(taskPath);
    if (buildTask == null) {
      failWithMessage(
          "Build task at path %s was not executed so could not be checked for outcome %s.%nTasks executed by build were:%n %s",
          taskPath,
          outcome,
          formatTasksForFailureMessage(actual.getTasks()));
    }
    if (buildTask.getOutcome() != outcome) {
      failWithMessage("Build task at path %s expected to have outcome:%n <%s>%nbut was:%n <%s>",
                      taskPath,
                      outcome,
                      buildTask.getOutcome());
    }
    return this;
  }

  private String formatTasksForFailureMessage(final List<BuildTask> tasks) {
    return tasks.stream()
                .sorted(Comparator.comparing(BuildTask::getPath))
                .map(buildTask -> "(path=" + buildTask.getPath() + ", " + "outcome="
                    + buildTask.getOutcome())
                .collect(Collectors.joining(", ", "[", "]"));
  }
}

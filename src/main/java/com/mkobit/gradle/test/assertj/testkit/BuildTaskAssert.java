package com.mkobit.gradle.test.assertj.testkit;

import org.assertj.core.api.AbstractAssert;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.TaskOutcome;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * Assertion methods for {@link BuildTask}.
 *
 * @see com.mkobit.gradle.test.assertj.GradleAssertions
 * @see com.mkobit.gradle.test.assertj.GradleSoftAssertions
 */
public class BuildTaskAssert extends AbstractAssert<BuildTaskAssert, BuildTask> {
  public BuildTaskAssert(@Nullable final BuildTask actual) {
    super(actual, BuildTaskAssert.class);
  }

  /**
   * Verifies that the {@link BuildTask#getPath()} ia equal to the provided path.
   * @param path the value to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code path} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is not equal to the provided {@code path}
   */
  public BuildTaskAssert pathIsEqualTo(final CharSequence path) {
    isNotNull();
    Objects.requireNonNull(path);

    final String pathValue = path.toString();
    if (!actual.getPath().equals(pathValue)) {
      failWithMessage("%nExpecting task path to be be equal to:%n <%s>%nbut was:%n <%s>", path, actual.getPath());
    }
    return this;
  }

  /**
   * Verifies that the {@link BuildTask#getPath()} starts with the provided prefix.
   * @param pathPrefix the prefix to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code pathPrefix} is {@code null}
   * @throws NullPointerException if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not start with the provided {@code pathPrefix}
   */
  public BuildTaskAssert pathStartsWith(final CharSequence pathPrefix) {
    isNotNull();
    Objects.requireNonNull(pathPrefix, "pathPrefix");

    final String pathValue = pathPrefix.toString();
    if (!actual.getPath().startsWith(pathValue)) {
      failWithMessage("%nExpecting task path to start with:%n <%s>%nbut was:%n <%s>", pathValue, actual.getPath());
    }
    return this;
  }

  /**
   * Verifies that the {@link BuildTask#getPath()} ends with the provided suffix.
   * @param pathSuffix the suffix to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code pathSuffix} is {@code null}.
   * @throws NullPointerException if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not end with the provided {@code pathSuffix}
   */
  public BuildTaskAssert pathEndsWith(final CharSequence pathSuffix) {
    isNotNull();
    Objects.requireNonNull(pathSuffix, "pathSuffix");

    final String pathValue = pathSuffix.toString();
    if (!actual.getPath().endsWith(pathValue)) {
      failWithMessage("%nExpecting task path to end with:%n <%s>%nbut was:%n <%s>", pathValue, actual.getPath());
    }
    return this;
  }

  /**
   * Verifies that the {@link BuildTask#getPath()} contains the provided sequence.
   * @param sequence the sequence to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code sequence} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} path does not contain with the provided {@code sequence}
   */
  public BuildTaskAssert pathContains(final CharSequence sequence) {
    isNotNull();
    Objects.requireNonNull(sequence, "sequence");

    final String pathValue = sequence.toString();
    if (!actual.getPath().contains(pathValue)) {
      failWithMessage("%nExpecting task path to contain:%n <%s>%nbut was:%n <%s>", pathValue, actual.getPath());
    }
    return this;
  }

  /**
   * Verifies that the {@link BuildTask#getPath()} matches the provided pattern.
   * @param pattern the pattern to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code pattern} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} path does not match the provided {@code pattern}
   */
  public BuildTaskAssert pathMatches(final Pattern pattern) {
    isNotNull();
    Objects.requireNonNull(pattern, "pattern");

    if (!pattern.matcher(actual.getPath()).matches()) {
      failWithMessage("%nExpecting task path to match:%n <%s>%nbut was:%n <%s>", pattern, actual.getPath());
    }
    return this;
  }

  /**
   * Verifies that the {@link BuildTask#getPath()} does not match the provided pattern.
   * @param pattern the pattern to test
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code pattern} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} path matches the provided {@code pattern}
   */
  public BuildTaskAssert pathDoesNotMatch(final Pattern pattern) {
    isNotNull();
    Objects.requireNonNull(pattern, "pattern");

    if (pattern.matcher(actual.getPath()).matches()) {
      failWithMessage("%nExpecting task path to match:%n <%s>%nbut was:%n <%s>", pattern, actual.getPath());
    }
    return this;
  }

  /**
   * Verifies that the {@link BuildTask#getPath()} satisfies the provided {@code }requirements}.
   * @param requirements the requirements for the path
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @see AbstractAssert#satisfies(Consumer)
   */
  public BuildTaskAssert pathSatisfies(final Consumer<String> requirements) {
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getPath());
    return this;
  }

  /**
   * Verifies that the outcome is {@link TaskOutcome#FAILED}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} does not have outcome {@link TaskOutcome#FAILED}
   */
  public BuildTaskAssert isFailed() {
    isNotNull();
    assertTaskOutcome(TaskOutcome.FAILED);
    return this;
  }

  /**
   * Verifies that the outcome is {@link TaskOutcome#FROM_CACHE}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not have outcome {@link TaskOutcome#FROM_CACHE}
   */
  public BuildTaskAssert isFromCache() {
    isNotNull();
    assertTaskOutcome(TaskOutcome.FROM_CACHE);
    return this;
  }

  /**
   * Verifies that the outcome is {@link TaskOutcome#NO_SOURCE}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not have outcome {@link TaskOutcome#NO_SOURCE}
   */
  public BuildTaskAssert isNoSource() {
    isNotNull();
    assertTaskOutcome(TaskOutcome.NO_SOURCE);
    return this;
  }

  /**
   * Verifies that the outcome is {@link TaskOutcome#SKIPPED}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not have outcome {@link TaskOutcome#SKIPPED}
   */
  public BuildTaskAssert isSkipped() {
    isNotNull();
    assertTaskOutcome(TaskOutcome.SKIPPED);
    return this;
  }

  /**
   * Verifies that the outcome is {@link TaskOutcome#SUCCESS}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not have outcome {@link TaskOutcome#SUCCESS}
   */
  public BuildTaskAssert isSuccess() {
    isNotNull();
    assertTaskOutcome(TaskOutcome.SUCCESS);
    return this;
  }

  /**
   * Verifies that the outcome is {@link TaskOutcome#UP_TO_DATE}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not have outcome {@link TaskOutcome#UP_TO_DATE}
   */
  public BuildTaskAssert isUpToDate() {
    isNotNull();
    assertTaskOutcome(TaskOutcome.UP_TO_DATE);
    return this;
  }

  /**
   * Verifies that the outcome is the provided {@code taskOutcome}.
   * @param taskOutcome the outcome to check the {@code actual} against
   * @return {@code this} assertion object
   * @throws NullPointerException if the provided {@code taskOutcome} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} does not the provided outcome
   * @see BuildTask#getOutcome()
   */
  public BuildTaskAssert hasTaskOutcome(final TaskOutcome taskOutcome) {
    isNotNull();
    assertTaskOutcome(taskOutcome);
    return this;
  }

  private void assertTaskOutcome(final TaskOutcome expected) {
    Objects.requireNonNull(expected, "expected");
    if (actual.getOutcome() != expected) {
      //failWithMessage("%nExpecting build output:%n <%s>%nto contain:%n <%s>", text, actual.getOutput());
      failWithMessage(
        "%nExpecting task at path %s to have outcome:%n <%s>%nbut was:%n <%s>",
        actual.getPath(),
        expected,
        actual.getOutcome()
      );
    }
  }
}

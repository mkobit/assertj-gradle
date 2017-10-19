package com.mkobit.gradle.test.assertj.artifacts;

import org.assertj.core.api.AbstractAssert;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationPublications;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.artifacts.PublishArtifactSet;
import org.gradle.api.artifacts.ResolvableDependencies;

import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Assertion methods for {@link Configuration}.
 *
 * @see com.mkobit.gradle.test.assertj.GradleAssertions
 * @see com.mkobit.gradle.test.assertj.GradleSoftAssertions
 */
// TODO: mkobit - decide if this should be AbstractIterableAssert rather than AbstractAssert
public class ConfigurationAssert extends AbstractAssert<ConfigurationAssert, Configuration> {
  public ConfigurationAssert(@Nullable final Configuration actual) {
    super(actual, ConfigurationAssert.class);
  }

  // resolutionStrategy

  /**
   * Verifies that the {@link Configuration#getState()} is {@link Configuration.State#UNRESOLVED}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} state is not {@link Configuration.State#UNRESOLVED}
   */
  public ConfigurationAssert hasUnresolvedState() {
    isNotNull();
    return hasState(Configuration.State.UNRESOLVED);
  }

  /**
   * Verifies that the {@link Configuration#getState()} is {@link Configuration.State#RESOLVED}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} state is not {@link Configuration.State#RESOLVED}
   */
  public ConfigurationAssert hasResolvedState() {
    isNotNull();
    return hasState(Configuration.State.RESOLVED);
  }

  /**
   * Verifies that the {@link Configuration#getState()} is {@link Configuration.State#RESOLVED_WITH_FAILURES}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} state is not {@link Configuration.State#RESOLVED_WITH_FAILURES}
   */
  public ConfigurationAssert hasResolvedWithFailuresState() {
    isNotNull();
    return hasState(Configuration.State.RESOLVED_WITH_FAILURES);
  }

  /**
   * Verifies that the {@link Configuration#getState()} is the provided {@code state}.
   * @param state value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} state is not equal to the provided {@code state}
   * @throws NullPointerException if the provided {@code state} is {@code null}
   */
  public ConfigurationAssert hasState(final Configuration.State state) {
    isNotNull();
    Objects.requireNonNull(state, "state");
    if (actual.getState() != state) {
      failWithMessage("%nExpecting configuration named [%s] to have state:%n <%s>%nbut was:%n <%s>", actual.getName(), state, actual.getState());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#getName()} is equal to the provided {@code name}.
   * @param name value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} name is not equal to the provided {@code name}
   * @throws NullPointerException if the provided {@code name} is {@code null}
   */
  public ConfigurationAssert hasNameEqualTo(final CharSequence name) {
    isNotNull();
    Objects.requireNonNull(name, "name");
    final String configurationName = name.toString();
    if (!actual.getName().equals(configurationName)) {
      failWithMessage("%nExpecting configuration name to be equal to:%n <%s>%nbut was:%n <%s>", configurationName, actual.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@code actual} is {@link Configuration#isVisible()} is {@code true}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is not visible
   */
  public ConfigurationAssert isVisible() {
    isNotNull();
    if (!actual.isVisible()) {
      failWithMessage("%nExpecting configuration named [%s] to be visible but was not", actual.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#isVisible()} is {@code false}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is visible
   */
  public ConfigurationAssert isNotVisible() {
    isNotNull();
    if (actual.isVisible()) {
      failWithMessage("%nExpecting configuration named [%s] to not be visible but was", actual.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#getExtendsFrom()} contains the provided {@code configuration}.
   * @param configuration value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is not contained in the {@code extendsFrom} configurations
   * @throws NullPointerException if the provided {@code configuration} is {@code null}
   */
  public ConfigurationAssert extendsFromContains(final Configuration configuration) {
    isNotNull();
    Objects.requireNonNull(configuration, "configuration");
    final Set<Configuration> extendsFrom = actual.getExtendsFrom();
    if (!extendsFrom.contains(configuration)) {
      // TODO: mkobit - improve failure message
      failWithMessage("%nExpecting configuration named [%s] extendsFrom to contain configuration:%n <%s>%nbut did not", actual.getName(), configuration.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#getExtendsFrom()} does not contain the provided {@code configuration}.
   * @param configuration value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is contained in the {@code extendsFrom} configurations
   * @throws NullPointerException if the provided {@code configuration} is {@code null}
   */
  public ConfigurationAssert extendsFromDoesNotContain(final Configuration configuration) {
    isNotNull();
    Objects.requireNonNull(configuration, "configuration");
    final Set<Configuration> extendsFrom = actual.getExtendsFrom();
    if (extendsFrom.contains(configuration)) {
      // TODO: mkobit - improve failure message
      failWithMessage("%nExpecting configuration named [%s] extendsFrom to not contain configuration:%n <%s>%nbut did", actual.getName(), configuration.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#getExtendsFrom()} satisfies the provided {@code requirements}.
   * @param requirements the requirements for the {@code extendsFrom} configurations
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert extendsFromSatisfies(final Consumer<Set<Configuration>> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getExtendsFrom());
    return this;
  }

  /**
   * Verifies that the {@link Configuration#isTransitive()} is {@code true}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is not {@code transitive}
   */
  public ConfigurationAssert isTransitive() {
    isNotNull();
    if (!actual.isTransitive()) {
      failWithMessage("%nExpecting configuration named [%s] to be transitive but was not", actual.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#isTransitive()} is {@code false}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} is {@code transitive}
   */
  public ConfigurationAssert isNotTransitive() {
    isNotNull();
    if (actual.isTransitive()) {
      failWithMessage("%nExpecting configuration named [%s] to not be transitive but was", actual.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#getDescription()} is equal to the provided {@code description}.
   * @param description value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} description is not equal to the provided {@code description}
   * @throws NullPointerException if the provided {@code description} is {@code null}
   */
  public ConfigurationAssert hasDescriptionEqualTo(final @Nullable CharSequence description) {
    isNotNull();
    final String descriptioValue = description != null ? description.toString() : null;
    if (!Objects.equals(actual.getDescription(), descriptioValue)) {
      failWithMessage("%nExpecting configuration named [%s] to have description equal to:%n <%s>%nbut was:%n <%s>", actual.getName(), descriptioValue, actual.getDescription());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#getHierarchy()} contains the provided {@code configuration}.
   * @param configuration value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code configuration} is {@code null}
   */
  public ConfigurationAssert hierarchyContains(final Configuration configuration) {
    isNotNull();
    Objects.requireNonNull(configuration, "configuration");
    final Set<Configuration> hierarchy = actual.getHierarchy();
    if (!hierarchy.contains(configuration)) {
      // TODO: mkobit - improve failure message
      failWithMessage("%nExpecting configuration named [%s] hierarchy to contain configuration:%n <%s>%nbut did not", actual.getName(), configuration);
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#getHierarchy()} does not contain provided {@code configuration}.
   * @param configuration value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} hierarchy does not contain the provided {@code configuration}
   * @throws NullPointerException if the provided {@code configuration} is {@code null}
   */
  public ConfigurationAssert hierarchyDoesNotContain(final Configuration configuration) {
    isNotNull();
    Objects.requireNonNull(configuration, "configuration");
    final Set<Configuration> hierarchy = actual.getHierarchy();
    if (hierarchy.contains(configuration)) {
      // TODO: mkobit - improve failure message
      failWithMessage("%nExpecting configuration named [%s] hierarchy to not contain configuration:%n <%s>%nbut did", actual.getName(), configuration);
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#getHierarchy()} satisfies the provided {@code requirements}.
   * @param requirements value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert hierarchySatisfies(final Consumer<Set<Configuration>> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getHierarchy());
    return this;
  }

  // ResolvedConfiguration - skipped for now

  /**
   * Verifies that the {@link Configuration#getUploadTaskName()} is equal to the provided {@code uploadTaskName}.
   * @param uploadTaskName value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} upload task name is not equal to the provided {@code uploadTaskName}
   * @throws NullPointerException if the provided {@code uploadTaskName} is {@code null}
   */
  public ConfigurationAssert uploadTaskNameEqualTo(final CharSequence uploadTaskName) {
    isNotNull();
    Objects.requireNonNull(uploadTaskName, "uploadTaskName");
    final String uploadTaskNameValue = uploadTaskName.toString();
    if (!actual.getUploadTaskName().equals(uploadTaskNameValue)) {
      failWithMessage("%nExpecting configuration named [%s] uploadTaskName to be equal to:%n <%s>%nbut was:%n <s>", actual.getName(), uploadTaskNameValue, actual.getUploadTaskName());
    }
    return this;
  }

  // BuildDependencies - skipped for now

  // getDependencies() - introduce friendlier APIs. It is a DomainObjectSet<Dependency>.

  /**
   * Verifies that the {@link Configuration#getDependencies()} satisfy the provided {@code requirements}.
   * @param requirements value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert dependenciesSatisfy(final Consumer<DependencySet> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getDependencies());
    return this;
  }

  // getAllDependencies() - introduce friendlier APIs. It is essentially a DomainObjectSet<Dependency>.

  /**
   * Verifies that the {@link Configuration#getAllDependencies()} satisfy the provided {@code requirements}.
   * @param requirements value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert allDependenciesSatisfy(final Consumer<DependencySet> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getAllDependencies());
    return this;
  }

  // getArtifacts() - introduce friendlier APIs. It is essentially a DomainObjectSet<PublishArtifact>.

  /**
   * Verifies that the {@link Configuration#getArtifacts()} satisfy the provided {@code requirements}.
   * @param requirements value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert artifactsSatisfy(final Consumer<PublishArtifactSet> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getArtifacts());
    return this;
  }
  // getAllArtifacts() - introduce friendlier APIs. It is essentially a DomainObjectSet<PublishArtifact>.

  /**
   * Verifies that the {@link Configuration#getAllArtifacts()} satisfy the provided {@code requirements}.
   * @param requirements value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert allArtifactsSatisfy(final Consumer<PublishArtifactSet> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getAllArtifacts());
    return this;
  }

  // getExcludeRules() - possibly friendlier APIs? Depends on usage

  /**
   * Verifies that the {@link Configuration#getExcludeRules()} satisfy the provided {@code requirements}.
   * @param requirements value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert excludeRulesSatisfy(final Consumer<Set<ExcludeRule>> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getExcludeRules());
    return this;
  }

  // getAll() - skipped for now
  // getIncoming()

  /**
   * Verifies that the {@link Configuration#getIncoming()} satisfy the provided {@code requirements}.
   * @param requirements value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert incomingSatisfy(final Consumer<ResolvableDependencies> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getIncoming());
    return this;
  }

  // getOutgoing() @Incubating

  /**
   * Verifies that the {@link Configuration#getOutgoing()} satisfy the provided {@code requirements}.
   * @param requirements value to assert against
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws NullPointerException if the provided {@code requirements} is {@code null}
   */
  public ConfigurationAssert outgoingSatisfy(final Consumer<ConfigurationPublications> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements, "requirements");
    requirements.accept(actual.getOutgoing());
    return this;
  }

  /**
   * Verifies that the {@link Configuration#isCanBeConsumed()} is {@code true}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} cannot be consumed
   */
  public ConfigurationAssert isCanBeConsumed() {
    isNotNull();
    if (!actual.isCanBeConsumed()) {
      failWithMessage("%nExpecting that configuration named [%s] can be consumed but it cannot", actual.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#isCanBeConsumed()} is {@code false}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} can be consumed
   */
  public ConfigurationAssert isNotCanBeConsumed() {
    isNotNull();
    if (actual.isCanBeConsumed()) {
      failWithMessage("%nExpecting that configuration named [%s] cannot be consumed but it can", actual.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#isCanBeResolved()} is {@code true}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} can be resolved
   */
  public ConfigurationAssert isCanBeResolved() {
    isNotNull();
    if (!actual.isCanBeResolved()) {
      failWithMessage("%nExpecting that configuration named [%s] can be resolved but it cannot", actual.getName());
    }
    return this;
  }

  /**
   * Verifies that the {@link Configuration#isCanBeResolved()} is {@code false}.
   * @return {@code this} assertion object
   * @throws AssertionError if the {@code actual} is {@code null}
   * @throws AssertionError if the {@code actual} cannot be resolved
   */
  public ConfigurationAssert isNotCanBeResolved() {
    isNotNull();
    if (actual.isCanBeResolved()) {
      failWithMessage("%nExpecting that configuration named [%s] cannot be resolved but it can", actual.getName());
    }
    return this;
  }
}

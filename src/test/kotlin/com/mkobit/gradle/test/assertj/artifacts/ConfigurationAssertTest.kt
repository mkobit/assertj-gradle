package com.mkobit.gradle.test.assertj.artifacts

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.ObjectEnumerableAssert
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationPublications
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.ExcludeRule
import org.gradle.api.artifacts.PublishArtifactSet
import org.gradle.api.artifacts.ResolvableDependencies
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import testsupport.assertNoExceptionThrownBy
import testsupport.assertThatAssertionErrorThrownBy
import java.util.function.Consumer

internal class ConfigurationAssertTest {

  private lateinit var mockConfiguration: Configuration
  private lateinit var configurationAssert: ConfigurationAssert

  @BeforeEach
  internal fun setUp() {
    mockConfiguration = mock()
    configurationAssert = ConfigurationAssert(mockConfiguration)
  }

  /**
   * This is mainly to document the decision (as of right now) to not make
   * the assertion an instance of {@link ObjectEnumerableAssert}.
   */
  @Test
  internal fun `is not an instance of ObjectEnumerableAssert`() {
    assertThat(configurationAssert).isNotInstanceOf(ObjectEnumerableAssert::class.java)
  }

  @Test
  internal fun `constructed with null Configuration instance`() {
    val nullActualConfigurationAssert = ConfigurationAssert(null)
    assertThatAssertionErrorThrownBy { nullActualConfigurationAssert.isNotNull }
    assertNoExceptionThrownBy { nullActualConfigurationAssert.isNull() }
  }

  @Test
  internal fun `has UNRESOLVED state`() {
    whenever(mockConfiguration.state).thenReturn(Configuration.State.UNRESOLVED)

    assertNoExceptionThrownBy { configurationAssert.hasState(Configuration.State.UNRESOLVED) }

    assertNoExceptionThrownBy { configurationAssert.hasUnresolvedState() }
    assertThatAssertionErrorThrownBy { configurationAssert.hasResolvedState() }
    assertThatAssertionErrorThrownBy { configurationAssert.hasResolvedWithFailuresState() }
  }

  @Test
  internal fun `has RESOLVED state`() {
    whenever(mockConfiguration.state).thenReturn(Configuration.State.RESOLVED)

    assertNoExceptionThrownBy { configurationAssert.hasState(Configuration.State.RESOLVED) }

    assertThatAssertionErrorThrownBy { configurationAssert.hasUnresolvedState() }
    assertNoExceptionThrownBy { configurationAssert.hasResolvedState() }
    assertThatAssertionErrorThrownBy { configurationAssert.hasResolvedWithFailuresState() }
  }

  @Test
  internal fun `has RESOLVED_WITH_FAILURES state`() {
    whenever(mockConfiguration.state).thenReturn(Configuration.State.RESOLVED_WITH_FAILURES)

    assertNoExceptionThrownBy { configurationAssert.hasState(Configuration.State.RESOLVED_WITH_FAILURES) }

    assertThatAssertionErrorThrownBy { configurationAssert.hasResolvedState() }
    assertThatAssertionErrorThrownBy { configurationAssert.hasUnresolvedState() }
    assertNoExceptionThrownBy { configurationAssert.hasResolvedWithFailuresState() }
  }

  @Test
  internal fun `has name equal to`() {
    whenever(mockConfiguration.name).thenReturn("confName")

    assertNoExceptionThrownBy { configurationAssert.hasNameEqualTo("confName") }
    assertThatAssertionErrorThrownBy { configurationAssert.hasNameEqualTo("notEqualToThis") }
  }

  @Test
  internal fun `is visible`() {
    whenever(mockConfiguration.isVisible).thenReturn(true, false)
    assertNoExceptionThrownBy { configurationAssert.isVisible }
    assertThatAssertionErrorThrownBy { configurationAssert.isVisible }
  }

  @Test
  internal fun `is not visible`() {
    whenever(mockConfiguration.isVisible).thenReturn(true, false)
    assertThatAssertionErrorThrownBy { configurationAssert.isNotVisible }
    assertNoExceptionThrownBy { configurationAssert.isNotVisible }
  }

  @Test
  internal fun `extends from contains`() {
    val mockAdditionalConfiguration: Configuration = mock()
    whenever(mockConfiguration.extendsFrom).thenReturn(setOf(mockAdditionalConfiguration), emptySet())
    assertNoExceptionThrownBy { configurationAssert.extendsFromContains(mockAdditionalConfiguration) }
    assertThatAssertionErrorThrownBy { configurationAssert.extendsFromContains(mockAdditionalConfiguration) }
  }

  @Test
  internal fun `extends from does not contain`() {
    val mockAdditionalConfiguration: Configuration = mock()
    whenever(mockConfiguration.extendsFrom).thenReturn(setOf(mockAdditionalConfiguration), emptySet())
    assertThatAssertionErrorThrownBy { configurationAssert.extendsFromDoesNotContain(mockAdditionalConfiguration) }
    assertNoExceptionThrownBy { configurationAssert.extendsFromDoesNotContain(mockAdditionalConfiguration) }
  }

  @Test
  internal fun `extends from satisfies`() {
    val extendsFrom: Set<Configuration> = mock()
    whenever(mockConfiguration.extendsFrom).thenReturn(extendsFrom)
    val mockRequirements: Consumer<Set<Configuration>> = mock()
    assertNoExceptionThrownBy { configurationAssert.extendsFromSatisfies(mockRequirements) }
    verify(mockRequirements).accept(extendsFrom)
  }

  @Test
  internal fun `is transitive`() {
    whenever(mockConfiguration.isTransitive).thenReturn(true, false)
    assertNoExceptionThrownBy { configurationAssert.isTransitive}
    assertThatAssertionErrorThrownBy { configurationAssert.isTransitive }
  }

  @Test
  internal fun `is not transitive`() {
    whenever(mockConfiguration.isTransitive).thenReturn(true, false)
    assertThatAssertionErrorThrownBy { configurationAssert.isNotTransitive }
    assertNoExceptionThrownBy { configurationAssert.isNotTransitive}
  }

  @Test
  internal fun `has description equal to`() {
    whenever(mockConfiguration.description).thenReturn("description")

    assertNoExceptionThrownBy { configurationAssert.hasDescriptionEqualTo("description") }
    assertThatAssertionErrorThrownBy { configurationAssert.hasDescriptionEqualTo("notEqualHere") }
  }

  @Test
  internal fun `hierarchy contains`() {
    val otherConfiguration: Configuration = mock()
    whenever(mockConfiguration.hierarchy).thenReturn(setOf(otherConfiguration), emptySet())
    assertNoExceptionThrownBy { configurationAssert.hierarchyContains(otherConfiguration) }
    assertThatAssertionErrorThrownBy { configurationAssert.hierarchyContains(otherConfiguration) }
  }

  @Test
  internal fun `hierarchy does not contain`() {
    val otherConfiguration: Configuration = mock()
    whenever(mockConfiguration.hierarchy).thenReturn(setOf(otherConfiguration), emptySet())
    assertThatAssertionErrorThrownBy { configurationAssert.hierarchyDoesNotContain(otherConfiguration) }
    assertNoExceptionThrownBy { configurationAssert.hierarchyDoesNotContain(otherConfiguration) }
  }

  @Test
  internal fun `hierarchy satisfies`() {
    val hierarchy: Set<Configuration> = mock()
    val mockRequirements: Consumer<Set<Configuration>> = mock()
    whenever(mockConfiguration.hierarchy).thenReturn(hierarchy)
    assertNoExceptionThrownBy { configurationAssert.hierarchySatisfies(mockRequirements) }
    verify(mockRequirements).accept(hierarchy)
  }

  @Test
  internal fun `upload task name equal to`() {
    whenever(mockConfiguration.uploadTaskName).thenReturn("upload")
    assertNoExceptionThrownBy { configurationAssert.uploadTaskNameEqualTo("upload") }
    assertThatAssertionErrorThrownBy { configurationAssert.uploadTaskNameEqualTo("notEqualHere") }
  }

  @Test
  internal fun `dependencies satisfy`() {
    val dependencies: DependencySet = mock()
    val mockRequirements: Consumer<DependencySet> = mock()
    whenever(mockConfiguration.dependencies).thenReturn(dependencies)
    assertNoExceptionThrownBy { configurationAssert.dependenciesSatisfy(mockRequirements) }
    verify(mockRequirements).accept(dependencies)
  }

  @Test
  internal fun `all dependencies satisfy`() {
    val allDependencies: DependencySet = mock()
    val mockRequirements: Consumer<DependencySet> = mock()
    whenever(mockConfiguration.allDependencies).thenReturn(allDependencies)
    assertNoExceptionThrownBy { configurationAssert.allDependenciesSatisfy(mockRequirements) }
    verify(mockRequirements).accept(allDependencies)
  }

  @Test
  internal fun `artifacts satisfy`() {
    val artifacts: PublishArtifactSet = mock()
    val mockRequirements: Consumer<PublishArtifactSet> = mock()
    whenever(mockConfiguration.artifacts).thenReturn(artifacts)
    assertNoExceptionThrownBy { configurationAssert.artifactsSatisfy(mockRequirements) }
    verify(mockRequirements).accept(artifacts)
  }

  @Test
  internal fun `all artifacts satisfy`() {
    val allArtifacts: PublishArtifactSet = mock()
    val mockRequirements: Consumer<PublishArtifactSet> = mock()
    whenever(mockConfiguration.allArtifacts).thenReturn(allArtifacts)
    assertNoExceptionThrownBy { configurationAssert.allArtifactsSatisfy(mockRequirements) }
    verify(mockRequirements).accept(allArtifacts)
  }

  @Test
  internal fun `exclude rules satisfy`() {
    val mockRequirements: Consumer<Set<ExcludeRule>> = mock()
    val excludeRules: Set<ExcludeRule> = mock()
    whenever(mockConfiguration.excludeRules).thenReturn(excludeRules)
    assertNoExceptionThrownBy { configurationAssert.excludeRulesSatisfy(mockRequirements) }
    verify(mockRequirements).accept(excludeRules)
  }

  @Test
  internal fun `incoming satisfy`() {
    val mockRequirements: Consumer<ResolvableDependencies> = mock()
    val incoming: ResolvableDependencies = mock()
    whenever(mockConfiguration.incoming).thenReturn(incoming)
    assertNoExceptionThrownBy { configurationAssert.incomingSatisfy(mockRequirements) }
    verify(mockRequirements).accept(incoming)
  }

  @Test
  internal fun `outgoing satisfy`() {
    val mockRequirements: Consumer<ConfigurationPublications> = mock()
    val outgoing: ConfigurationPublications = mock()
    whenever(mockConfiguration.outgoing).thenReturn(outgoing)
    assertNoExceptionThrownBy { configurationAssert.outgoingSatisfy(mockRequirements) }
    verify(mockRequirements).accept(outgoing)
  }

  @Test
  internal fun `is can be consumed`() {
    whenever(mockConfiguration.isCanBeConsumed).thenReturn(true, false)
    assertNoExceptionThrownBy { configurationAssert.isCanBeConsumed }
    assertThatAssertionErrorThrownBy { configurationAssert.isCanBeConsumed }
  }

  @Test
  internal fun `is cannot be consumed`() {
    whenever(mockConfiguration.isCanBeConsumed).thenReturn(true, false)
    assertThatAssertionErrorThrownBy { configurationAssert.isNotCanBeConsumed }
    assertNoExceptionThrownBy { configurationAssert.isNotCanBeConsumed }
  }

  @Test
  internal fun `is can be resolved`() {
    whenever(mockConfiguration.isCanBeResolved).thenReturn(true, false)
    assertNoExceptionThrownBy { configurationAssert.isCanBeResolved }
    assertThatAssertionErrorThrownBy { configurationAssert.isCanBeResolved }
  }

  @Test
  internal fun `is cannot be resolved`() {
    whenever(mockConfiguration.isCanBeResolved).thenReturn(true, false)
    assertThatAssertionErrorThrownBy { configurationAssert.isNotCanBeResolved }
    assertNoExceptionThrownBy { configurationAssert.isNotCanBeResolved }
  }
}

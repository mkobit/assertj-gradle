package com.mkobit.gradle.test.assertj.artifacts

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.ObjectEnumerableAssert
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationPublications
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.ExcludeRule
import org.gradle.api.artifacts.PublishArtifactSet
import org.gradle.api.artifacts.ResolvableDependencies
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
    assertThatThrownBy { nullActualConfigurationAssert.isNotNull }.isInstanceOf(AssertionError::class.java)
    assertThatCode { nullActualConfigurationAssert.isNull() }.doesNotThrowAnyException()
  }

  @Test
  internal fun `has UNRESOLVED state`() {
    whenever(mockConfiguration.state).thenReturn(Configuration.State.UNRESOLVED)

    assertThatCode { configurationAssert.hasState(Configuration.State.UNRESOLVED) }.doesNotThrowAnyException()

    assertThatCode { configurationAssert.hasUnresolvedState() }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.hasResolvedState() }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { configurationAssert.hasResolvedWithFailuresState() }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `has RESOLVED state`() {
    whenever(mockConfiguration.state).thenReturn(Configuration.State.RESOLVED)

    assertThatCode { configurationAssert.hasState(Configuration.State.RESOLVED) }.doesNotThrowAnyException()

    assertThatThrownBy { configurationAssert.hasUnresolvedState() }.isInstanceOf(AssertionError::class.java)
    assertThatCode { configurationAssert.hasResolvedState() }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.hasResolvedWithFailuresState() }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `has RESOLVED_WITH_FAILURES state`() {
    whenever(mockConfiguration.state).thenReturn(Configuration.State.RESOLVED_WITH_FAILURES)

    assertThatCode { configurationAssert.hasState(Configuration.State.RESOLVED_WITH_FAILURES) }.doesNotThrowAnyException()

    assertThatThrownBy { configurationAssert.hasResolvedState() }.isInstanceOf(AssertionError::class.java)
    assertThatThrownBy { configurationAssert.hasUnresolvedState() }.isInstanceOf(AssertionError::class.java)
    assertThatCode { configurationAssert.hasResolvedWithFailuresState() }.doesNotThrowAnyException()
  }

  @Test
  internal fun `has name equal to`() {
    whenever(mockConfiguration.name).thenReturn("confName")

    assertThatCode { configurationAssert.hasNameEqualTo("confName") }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.hasNameEqualTo("notEqualToThis") }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `is visible`() {
    whenever(mockConfiguration.isVisible).thenReturn(true, false)
    assertThatCode { configurationAssert.isVisible }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.isVisible }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `is not visible`() {
    whenever(mockConfiguration.isVisible).thenReturn(true, false)
    assertThatThrownBy { configurationAssert.isNotVisible }.isInstanceOf(AssertionError::class.java)
    assertThatCode { configurationAssert.isNotVisible }.doesNotThrowAnyException()
  }

  @Test
  internal fun `extends from contains`() {
    val mockAdditionalConfiguration: Configuration = mock()
    whenever(mockConfiguration.extendsFrom).thenReturn(setOf(mockAdditionalConfiguration), emptySet())
    assertThatCode { configurationAssert.extendsFromContains(mockAdditionalConfiguration) }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.extendsFromContains(mockAdditionalConfiguration) }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `extends from does not contain`() {
    val mockAdditionalConfiguration: Configuration = mock()
    whenever(mockConfiguration.extendsFrom).thenReturn(setOf(mockAdditionalConfiguration), emptySet())
    assertThatThrownBy { configurationAssert.extendsFromDoesNotContain(mockAdditionalConfiguration) }.isInstanceOf(AssertionError::class.java)
    assertThatCode { configurationAssert.extendsFromDoesNotContain(mockAdditionalConfiguration) }.doesNotThrowAnyException()
  }

  @Test
  internal fun `extends from satisfies`() {
    val extendsFrom: Set<Configuration> = mock()
    whenever(mockConfiguration.extendsFrom).thenReturn(extendsFrom)
    val mockRequirements: Consumer<Set<Configuration>> = mock()
    assertThatCode { configurationAssert.extendsFromSatisfies(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(extendsFrom)
  }

  @Test
  internal fun `is transitive`() {
    whenever(mockConfiguration.isTransitive).thenReturn(true, false)
    assertThatCode { configurationAssert.isTransitive}.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.isTransitive }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `is not transitive`() {
    whenever(mockConfiguration.isTransitive).thenReturn(true, false)
    assertThatThrownBy { configurationAssert.isNotTransitive }.isInstanceOf(AssertionError::class.java)
    assertThatCode { configurationAssert.isNotTransitive}.doesNotThrowAnyException()
  }

  @Test
  internal fun `has description equal to`() {
    whenever(mockConfiguration.description).thenReturn("description")

    assertThatCode { configurationAssert.hasDescriptionEqualTo("description") }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.hasDescriptionEqualTo("notEqualHere") }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `hierarchy contains`() {
    val otherConfiguration: Configuration = mock()
    whenever(mockConfiguration.hierarchy).thenReturn(setOf(otherConfiguration), emptySet())
    assertThatCode { configurationAssert.hierarchyContains(otherConfiguration) }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.hierarchyContains(otherConfiguration) }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `hierarchy does not contain`() {
    val otherConfiguration: Configuration = mock()
    whenever(mockConfiguration.hierarchy).thenReturn(setOf(otherConfiguration), emptySet())
    assertThatThrownBy { configurationAssert.hierarchyDoesNotContain(otherConfiguration) }.isInstanceOf(AssertionError::class.java)
    assertThatCode { configurationAssert.hierarchyDoesNotContain(otherConfiguration) }.doesNotThrowAnyException()
  }

  @Test
  internal fun `hierarchy satisfies`() {
    val hierarchy: Set<Configuration> = mock()
    val mockRequirements: Consumer<Set<Configuration>> = mock()
    whenever(mockConfiguration.hierarchy).thenReturn(hierarchy)
    assertThatCode { configurationAssert.hierarchySatisfies(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(hierarchy)
  }

  @Test
  internal fun `upload task name equal to`() {
    whenever(mockConfiguration.uploadTaskName).thenReturn("upload")
    assertThatCode { configurationAssert.uploadTaskNameEqualTo("upload") }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.uploadTaskNameEqualTo("notEqualHere") }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `dependencies satisfy`() {
    val dependencies: DependencySet = mock()
    val mockRequirements: Consumer<DependencySet> = mock()
    whenever(mockConfiguration.dependencies).thenReturn(dependencies)
    assertThatCode { configurationAssert.dependenciesSatisfy(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(dependencies)
  }

  @Test
  internal fun `all dependencies satisfy`() {
    val allDependencies: DependencySet = mock()
    val mockRequirements: Consumer<DependencySet> = mock()
    whenever(mockConfiguration.allDependencies).thenReturn(allDependencies)
    assertThatCode { configurationAssert.allDependenciesSatisfy(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(allDependencies)
  }

  @Test
  internal fun `artifacts satisfy`() {
    val artifacts: PublishArtifactSet = mock()
    val mockRequirements: Consumer<PublishArtifactSet> = mock()
    whenever(mockConfiguration.artifacts).thenReturn(artifacts)
    assertThatCode { configurationAssert.artifactsSatisfy(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(artifacts)
  }

  @Test
  internal fun `all artifacts satisfy`() {
    val allArtifacts: PublishArtifactSet = mock()
    val mockRequirements: Consumer<PublishArtifactSet> = mock()
    whenever(mockConfiguration.allArtifacts).thenReturn(allArtifacts)
    assertThatCode { configurationAssert.allArtifactsSatisfy(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(allArtifacts)
  }

  @Test
  internal fun `exclude rules satisfy`() {
    val mockRequirements: Consumer<Set<ExcludeRule>> = mock()
    val excludeRules: Set<ExcludeRule> = mock()
    whenever(mockConfiguration.excludeRules).thenReturn(excludeRules)
    assertThatCode { configurationAssert.excludeRulesSatisfy(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(excludeRules)
  }

  @Test
  internal fun `incoming satisfy`() {
    val mockRequirements: Consumer<ResolvableDependencies> = mock()
    val incoming: ResolvableDependencies = mock()
    whenever(mockConfiguration.incoming).thenReturn(incoming)
    assertThatCode { configurationAssert.incomingSatisfy(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(incoming)
  }

  @Test
  internal fun `outgoing satisfy`() {
    val mockRequirements: Consumer<ConfigurationPublications> = mock()
    val outgoing: ConfigurationPublications = mock()
    whenever(mockConfiguration.outgoing).thenReturn(outgoing)
    assertThatCode { configurationAssert.outgoingSatisfy(mockRequirements) }.doesNotThrowAnyException()
    verify(mockRequirements).accept(outgoing)
  }

  @Test
  internal fun `is can be consumed`() {
    whenever(mockConfiguration.isCanBeConsumed).thenReturn(true, false)
    assertThatCode { configurationAssert.isCanBeConsumed }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.isCanBeConsumed }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `is cannot be consumed`() {
    whenever(mockConfiguration.isCanBeConsumed).thenReturn(true, false)
    assertThatThrownBy { configurationAssert.isNotCanBeConsumed }.isInstanceOf(AssertionError::class.java)
    assertThatCode { configurationAssert.isNotCanBeConsumed }.doesNotThrowAnyException()
  }

  @Test
  internal fun `is can be resolved`() {
    whenever(mockConfiguration.isCanBeResolved).thenReturn(true, false)
    assertThatCode { configurationAssert.isCanBeResolved }.doesNotThrowAnyException()
    assertThatThrownBy { configurationAssert.isCanBeResolved }.isInstanceOf(AssertionError::class.java)
  }

  @Test
  internal fun `is cannot be resolved`() {
    whenever(mockConfiguration.isCanBeResolved).thenReturn(true, false)
    assertThatThrownBy { configurationAssert.isNotCanBeResolved }.isInstanceOf(AssertionError::class.java)
    assertThatCode { configurationAssert.isNotCanBeResolved }.doesNotThrowAnyException()
  }
}

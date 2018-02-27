package com.mkobit.gradle.test.assertj.container;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractIterableAssert;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Rule;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractNamedDomainObjectContainerAssert<SELF extends AbstractNamedDomainObjectContainerAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT>,
    ACTUAL extends NamedDomainObjectContainer<? extends ELEMENT>,
    ELEMENT,
    ELEMENT_ASSERT extends AbstractAssert<ELEMENT_ASSERT, ELEMENT>>
    extends AbstractIterableAssert<SELF, ACTUAL, ELEMENT, ELEMENT_ASSERT> {


  public AbstractNamedDomainObjectContainerAssert(
      @Nullable final ACTUAL elements,
      final Class<?> selfType
  ) {
    super(elements, selfType);
  }

  public SELF containsName(final CharSequence name) {
    isNotNull();
    Objects.requireNonNull(name, "name");

    final String nameValue = name.toString();
    final ELEMENT byName = actual.findByName(nameValue);
    if (byName == null) {
      // TODO
      failWithMessage("");
    }

    return myself;
  }

  public SELF doesNotContainName(final CharSequence name) {
    isNotNull();
    Objects.requireNonNull(name, "name");

    final String nameValue = name.toString();
    final ELEMENT byName = actual.findByName(nameValue);
    if (byName != null) {
      // TODO
      failWithMessage("");
    }

    return myself;
  }

  public SELF containsNameSatisfying(final CharSequence name, final Consumer<ELEMENT> requirements) {
    isNotNull();
    Objects.requireNonNull(name, "name");
    Objects.requireNonNull(requirements, "requirements");

    final String nameValue = name.toString();
    final ELEMENT byName = actual.findByName(nameValue);
    if (byName == null) {
      // TODO
      failWithMessage("");
    }
    requirements.accept(byName);

    return myself;
  }

  public SELF hasRulesSatisfying(final Consumer<List<Rule>> requirements) {
    isNotNull();
    Objects.requireNonNull(requirements);

    requirements.accept(actual.getRules());
    return myself;
  }

  @Override
  protected ELEMENT_ASSERT toAssert(final ELEMENT value, final String description) {
    // ???
    return null;
  }

}

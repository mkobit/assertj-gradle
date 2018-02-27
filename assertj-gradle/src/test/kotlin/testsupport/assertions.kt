package testsupport

import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.ThrowableAssertAlternative

/**
 * Kotlin friendly [SoftAssertions.assertSoftly].
 */
fun assertSoftly(verify: SoftAssertions.() -> Unit) = SoftAssertions.assertSoftly { it.verify() }

fun assertThatAssertionErrorThrownBy(code: () -> Unit): ThrowableAssertAlternative<AssertionError> =
    assertThatExceptionOfType(AssertionError::class.java).isThrownBy { code() }

fun assertNoExceptionThrownBy(code: () -> Unit): Unit = assertThatCode { code() }.doesNotThrowAnyException()

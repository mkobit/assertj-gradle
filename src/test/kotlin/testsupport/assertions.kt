package testsupport

import org.assertj.core.api.AbstractThrowableAssert
import org.assertj.core.api.SoftAssertions

/**
 * Kotlin friendly [SoftAssertions.assertSoftly].
 */
fun assertSoftly(verify: SoftAssertions.() -> Unit) = SoftAssertions.assertSoftly { it.verify() }

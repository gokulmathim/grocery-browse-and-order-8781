package org.example.app

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * PUBLIC_INTERFACE
 * Simple smoke test to ensure unit test discovery succeeds in CI.
 */
class SmokeTest {
    @Test
    fun testAlwaysPasses() {
        assertTrue(true)
    }
}

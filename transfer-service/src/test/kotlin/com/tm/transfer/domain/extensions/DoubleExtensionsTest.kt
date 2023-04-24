package com.tm.transfer.domain.extensions

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DoubleExtensionsTest {

    @Test
    fun `Should convert double to percentage`() {
        val randomDouble = 1500.0
        val expected = 15.0

        val result = randomDouble.toPercentage()

        assertEquals(result, expected)
    }

    @Test
    fun `Should set double with 2 decimals places`() {
        val double = 1500.543
        val expected = 1500.54

        val result = double.setRoundTwoPlaces()

        assertEquals(result, expected)
    }
}

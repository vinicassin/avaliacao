package com.tm.transfer.domain.utils

import com.tm.transfer.application.exceptions.InternalException
import com.tm.transfer.application.exceptions.InvalidScheduledDateException
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class CalculateTaxTest {

    @Test
    fun `Should calculate tax A calling calculatedTax fun with a current date`() {
        val expected = 106.0
        val result = calculatedTax("A", 100.0, scheduledDate = LocalDate.now())

        assertEquals(result, expected)
    }

    @Test
    fun `Should throw calculate tax A calling calculatedTax fun with a future date`() {
        assertThrows<InvalidScheduledDateException> {
            calculatedTax("A", 100.0, scheduledDate = LocalDate.now().plusDays(1))
        }
    }

    @Test
    fun `Should calculate tax B calling calculatedTax fun with a date between 1 to 10 days in the future`() {
        val expected = 112.0
        val result = calculatedTax("B", 100.0, scheduledDate = LocalDate.now().plusDays(2))

        assertEquals(result, expected)
    }

    @Test
    fun `Should throw calculate tax B calling calculatedTax fun with a current date`() {
        assertThrows<InvalidScheduledDateException> {
            calculatedTax("B", 100.0, scheduledDate = LocalDate.now())
        }
    }

    @Test
    fun `Should throw calculate tax B calling calculatedTax fun with a 11 days in the future`() {
        assertThrows<InvalidScheduledDateException> {
            calculatedTax("B", 100.0, scheduledDate = LocalDate.now().plusDays(11))
        }
    }

    @Test
    fun `Should calculate tax C calling calculatedTax fun with a date between 11 to 20 days in the future`() {
        val expected = 108.2
        val result = calculatedTax("C", 100.0, scheduledDate = LocalDate.now().plusDays(11))

        assertEquals(result, expected)
    }

    @Test
    fun `Should calculate tax C calling calculatedTax fun with a date between 21 to 30 days in the future`() {
        val expected = 106.9
        val result = calculatedTax("C", 100.0, scheduledDate = LocalDate.now().plusDays(21))

        assertEquals(result, expected)
    }

    @Test
    fun `Should calculate tax C calling calculatedTax fun with a date between 31 to 40 days in the future`() {
        val expected = 104.7
        val result = calculatedTax("C", 100.0, scheduledDate = LocalDate.now().plusDays(31))

        assertEquals(result, expected)
    }

    @Test
    fun `Should calculate tax C calling calculatedTax fun with a date between 41 days or more in the future`() {
        val expected = 101.7
        val result = calculatedTax("C", 100.0, scheduledDate = LocalDate.now().plusDays(41))

        assertEquals(result, expected)
    }

    @Test
    fun `Should throw calculate tax C calling calculatedTax fun with a date less than 11 days in the future`() {
        assertThrows<InvalidScheduledDateException> {
            calculatedTax("C", 100.0, scheduledDate = LocalDate.now().plusDays(10))
        }
    }

    // D0 utilizar valores

    @Test
    fun `Should calculate tax D calling calculatedTax fun with a value between 0 to 1000`() {
        val expected = 106.0
        val result = calculatedTax("D", 100.0, scheduledDate = LocalDate.now())

        assertEquals(result, expected)
    }

    @Test
    fun `Should calculate tax D calling calculatedTax fun with a value between 1001 to 2000`() {
        val expected = 1013.0
        val result = calculatedTax("D", 1001.0, scheduledDate = LocalDate.now().plusDays(2))

        assertEquals(result, expected)
    }

    @Test
    fun `Should calculate tax D calling calculatedTax fun with a value 2001 or more`() {
        val expected = 2166.16
        val result = calculatedTax("D", 2002.0, scheduledDate = LocalDate.now().plusDays(11))

        assertEquals(result, expected)
    }

    @Test
    fun `Should throw calculate tax D calling calculatedTax fun with negative value`() {
        assertThrows<InternalException> {
            calculatedTax("D", -1.0, scheduledDate = LocalDate.now())
        }
    }
}
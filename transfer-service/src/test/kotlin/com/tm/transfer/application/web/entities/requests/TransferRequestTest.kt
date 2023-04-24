package com.tm.transfer.application.web.entities.requests

import com.tm.transfer.application.exceptions.InvalidParameterException
import com.tm.transfer.builders.builderTransferRequest
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class TransferRequestTest {

    @Test
    fun `Should validate request params when value is less than 0`() {
        val request = builderTransferRequest(value = -1.0)

        assertThrows<InvalidParameterException> {
            validateRequestParams(request)
        }
    }

    @Test
    fun `Should validate request params when scheduleDate is before than current date`() {
        val request = builderTransferRequest(scheduleDate = LocalDate.now().minusDays(1))

        assertThrows<InvalidParameterException> {
            validateRequestParams(request)
        }
    }

    @Test
    fun `Should validate request params when taxType was different than valid types`() {
        val request = builderTransferRequest(taxType = "E")

        assertThrows<InvalidParameterException> {
            validateRequestParams(request)
        }
    }
}
package com.tm.transfer.domain.services

import com.tm.transfer.application.exceptions.InsufficientBalanceException
import com.tm.transfer.builders.buildTransferData
import com.tm.transfer.builders.buildTransferDataEntity
import com.tm.transfer.domain.repositories.TransferRepository
import com.tm.transfer.resources.repositories.toDomain
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TransferServiceTest {

    @MockK
    private lateinit var repository: TransferRepository

    @InjectMockKs
    private lateinit var service: TransferService

    @Test
    fun `Should create transfer with taxType A`() {
        val builderTransferDataEntity = buildTransferDataEntity(valueWithTax = 1033.0)
        val builderTransferData = buildTransferData()

        val expected = builderTransferDataEntity.toDomain()

        every { repository.save(any()) } returns builderTransferDataEntity

        val result = service.createTransfer(builderTransferData)

        assertEquals(result, expected)
    }

    @Test
    fun `Should throw create transfer when value is less than balance`() {
        val builderTransferData = buildTransferData(transactionValue = 1500.0 ,valueWithTax = 1548.0)

        assertThrows<InsufficientBalanceException> {
            service.createTransfer(builderTransferData)
        }
    }

    @Test
    fun `Should return all transfer for shipping account`() {
        val mockkList = listOf(
            buildTransferDataEntity(valueWithTax = 1033.0),
            buildTransferDataEntity(transferId = "2", valueWithTax = 1033.0)
        )

        every { repository.findAllByShippingAccount(DEFAULT_SHIPPING_ACCOUNT) } returns mockkList

        val result = service.findByShippingAccount(DEFAULT_SHIPPING_ACCOUNT)

        val expected = mockkList.map {it.toDomain()}

        assertEquals(result, expected)
        verify(exactly = 1) {repository.findAllByShippingAccount(any())}
    }

    companion object {
        private const val DEFAULT_SHIPPING_ACCOUNT = "12345"
    }
}

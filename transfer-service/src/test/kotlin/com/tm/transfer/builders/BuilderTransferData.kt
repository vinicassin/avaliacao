package com.tm.transfer.builders

import com.tm.transfer.domain.TransferData
import java.time.LocalDate
import java.time.LocalDateTime

fun buildTransferData(
    transferId: String? = null,
    shippingAccount: Long = 12345,
    destinationAccount: Long = 54321,
    transactionValue: Double = 1000.0,
    taxType: String = "A",
    valueWithTax: Double? = null,
    scheduleDate: LocalDate = LocalDate.now(),
    transferDate: LocalDate = LocalDate.now(),
    createdAt: LocalDateTime? = null,
) = TransferData(
    transferId = transferId,
    shippingAccount = shippingAccount,
    destinationAccount = destinationAccount,
    transactionValue = transactionValue,
    taxType = taxType,
    valueWithTax = valueWithTax,
    scheduleDate = scheduleDate,
    transferDate = transferDate,
    createdAt = createdAt,
)
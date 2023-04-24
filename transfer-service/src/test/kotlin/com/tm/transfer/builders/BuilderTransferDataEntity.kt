package com.tm.transfer.builders

import com.tm.transfer.resources.repositories.TransferDataEntity
import java.time.LocalDate
import java.time.LocalDateTime

fun buildTransferDataEntity(
    transferId: String = "1",
    shippingAccount: Long = 12345,
    destinationAccount: Long = 54321,
    transactionValue: Double = 1000.0,
    taxType: String = "A",
    valueWithTax: Double,
    scheduleDate: LocalDate = LocalDate.now(),
    transferDate: LocalDate = LocalDate.now(),
    createdAt: LocalDateTime = LocalDateTime.now(),
) = TransferDataEntity(
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

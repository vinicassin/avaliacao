package com.tm.transfer.domain

import java.time.LocalDate
import java.time.LocalDateTime

data class TransferData(
    val transferId: String? = null,
    val shippingAccount: Long,
    val destinationAccount: Long,
    val valueTransfered: Double,
    val valueWithTax: Double? = null,
    val scheduleDate: LocalDate,
    val transferDate: LocalDate,
    val createdAt: LocalDateTime? = null,
)

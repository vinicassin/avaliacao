package com.tm.transfer.application.web.entities.responses

import java.time.LocalDate
import java.time.LocalDateTime

data class TransferResponse(
    val transferId: String,
    val shippingAccount: Long,
    val destinationAccount: Long,
    val valueTransfered: Double,
    val valueWithTax: Double,
    val scheduleDate: LocalDate,
    val transferDate: LocalDate,
    val createdAt: LocalDateTime,
)
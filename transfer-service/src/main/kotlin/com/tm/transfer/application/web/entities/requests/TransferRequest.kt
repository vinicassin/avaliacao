package com.tm.transfer.application.web.entities.requests

import java.time.LocalDate

data class TransferRequest(
    val shippingAccount: Long,
    val destinationAccount: Long,
    val value: Double,
    val scheduleDate: LocalDate,
)

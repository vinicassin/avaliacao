package com.tm.transfer.builders

import com.tm.transfer.application.web.entities.requests.TransferRequest
import java.time.LocalDate

fun builderTransferRequest(
    shippingAccount: Long = 123456,
    destinationAccount: Long = 54321,
    value: Double = 1000.0,
    taxType: String = "A",
    scheduleDate: LocalDate = LocalDate.now()
) = TransferRequest(
    shippingAccount = shippingAccount,
    destinationAccount = destinationAccount,
    value = value,
    taxType = taxType,
    scheduleDate = scheduleDate,
)
package com.tm.transfer.application.web.entities.requests

import com.tm.transfer.application.exceptions.InvalidParameterException
import com.tm.transfer.domain.TransferData
import java.time.LocalDate

data class TransferRequest(
    val shippingAccount: Long,
    val destinationAccount: Long,
    val value: Double,
    val taxType: String,
    val scheduleDate: LocalDate,
)

fun TransferRequest.toDomain() = TransferData(
    shippingAccount = this.shippingAccount,
    destinationAccount = this.destinationAccount,
    transactionValue = this.value,
    taxType = this.taxType.uppercase(),
    scheduleDate = this.scheduleDate,
    transferDate = LocalDate.now(),
)

fun validateRequestParams(request: TransferRequest) {
    if(request.value <= 0.0) {
        throw InvalidParameterException("Value must be greater than 0.0")
    }
    if(request.scheduleDate.isBefore(LocalDate.now())) {
        throw InvalidParameterException("Schedule date needs to be greater than current date")
    }
    if(!taxTypes.contains(request.taxType.uppercase())) {
        throw InvalidParameterException("Invalid taxType, valid taxTypes: A, B, C or D")
    }
}

private val taxTypes = listOf("A","B", "C", "D")

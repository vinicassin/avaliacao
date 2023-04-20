package com.tm.transfer.domain

import com.tm.transfer.application.web.entities.responses.TransferResponse
import java.time.LocalDate
import java.time.LocalDateTime

data class TransferData(
    val transferId: String? = null,
    val shippingAccount: Long,
    val destinationAccount: Long,
    val transactionValue: Double,
    val valueWithTax: Double? = null,
    val taxType: String,
    val scheduleDate: LocalDate,
    val transferDate: LocalDate,
    val createdAt: LocalDateTime? = null,
)

fun TransferData.toResponse() = TransferResponse(
    transferId = this.transferId!!,
    shippingAccount = this.shippingAccount,
    destinationAccount = this.destinationAccount,
    transactionValue = this.transactionValue,
    taxType = this.taxType,
    valueWithTax = this.valueWithTax!!,
    scheduleDate = this.scheduleDate,
    transferDate = this.transferDate,
    createdAt = this.createdAt!!,
)

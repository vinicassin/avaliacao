package com.tm.transfer.domain

import com.tm.transfer.application.web.entities.responses.TransferResponse
import com.tm.transfer.resources.repositories.TransferDataEntity
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

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

fun TransferData.toEntity(): TransferDataEntity = TransferDataEntity(
    transferId = UUID.randomUUID().toString(),
    shippingAccount = this.shippingAccount,
    destinationAccount = this.destinationAccount,
    transactionValue = this.transactionValue,
    taxType = this.taxType,
    valueWithTax = this.valueWithTax,
    scheduleDate = this.scheduleDate,
    transferDate = this.transferDate,
    createdAt = LocalDateTime.now(),
)

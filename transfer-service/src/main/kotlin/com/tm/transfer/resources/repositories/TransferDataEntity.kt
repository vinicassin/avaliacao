package com.tm.transfer.resources.repositories

import com.tm.transfer.domain.TransferData
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "transfers")
class TransferDataEntity (
    @Id
    var transferId: String? = null,
    var shippingAccount: Long? = null,
    var destinationAccount: Long? = null,
    var transactionValue: Double? = null,
    var taxType: String? = null,
    var valueWithTax: Double? = null,
    var scheduleDate: LocalDate? = null,
    var transferDate: LocalDate? = null,
    var createdAt: LocalDateTime? = null,
)

fun TransferDataEntity.toDomain(): TransferData =
    TransferData(
        transferId = this.transferId,
        shippingAccount = this.shippingAccount!!,
        destinationAccount = this.destinationAccount!!,
        transactionValue = this.transactionValue!!,
        taxType = this.taxType!!,
        valueWithTax = this.valueWithTax!!,
        scheduleDate = this.scheduleDate!!,
        transferDate = this.transferDate!!,
        createdAt = this.createdAt!!,
    )

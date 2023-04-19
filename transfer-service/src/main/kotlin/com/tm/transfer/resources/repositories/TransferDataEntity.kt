package com.tm.transfer.resources.repositories

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
    var valueTransfered: Double? = null,
    var valueWithTax: Double? = null,
    var scheduleDate: LocalDate? = null,
    var transferDate: LocalDate? = null,
    var createdAt: LocalDateTime? = null,
)

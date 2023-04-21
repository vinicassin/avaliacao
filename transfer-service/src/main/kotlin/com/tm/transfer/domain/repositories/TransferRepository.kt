package com.tm.transfer.domain.repositories

import com.tm.transfer.resources.repositories.TransferDataEntity

interface TransferRepository {
    fun save(transferDataEntity: TransferDataEntity) : TransferDataEntity
    fun findAllByShippingAccount(shippingAccount: String) : List<TransferDataEntity>
}
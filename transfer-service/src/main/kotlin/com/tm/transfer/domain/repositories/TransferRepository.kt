package com.tm.transfer.domain.repositories

import com.tm.transfer.domain.TransferData

interface TransferRepository {
    fun save(transferData: TransferData) : TransferData
    fun findByShippingAccount(shippingAccount: String) : List<TransferData>?
}
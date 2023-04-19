package com.tm.transfer.domain

interface TransferRepository {
    fun save(transferData: TransferData) : TransferData
}
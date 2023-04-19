package com.tm.transfer.resources.repositories

import com.tm.transfer.domain.TransferRepository
import com.tm.transfer.domain.TransferData
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Component
class TransferSql(
    private val repository: TransferRepositoryJPA
) : TransferRepository {

    override fun save(transferData: TransferData) : TransferData {
        val entity = TransferDataEntity(
            transferId = UUID.randomUUID().toString(),
            shippingAccount = transferData.shippingAccount,
            destinationAccount = transferData.destinationAccount,
            valueTransfered = transferData.valueTransfered,
            valueWithTax = transferData.valueWithTax,
            scheduleDate = transferData.scheduleDate,
            transferDate = transferData.transferDate,
            createdAt = LocalDateTime.now(),
        )

        repository.save(entity)

        return transferData.copy(transferId = entity.transferId, createdAt = entity.createdAt)
    }
}

@Repository
interface TransferRepositoryJPA : CrudRepository<TransferDataEntity, String>

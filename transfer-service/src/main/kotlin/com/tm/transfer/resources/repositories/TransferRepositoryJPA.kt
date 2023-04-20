package com.tm.transfer.resources.repositories

import com.tm.transfer.application.exceptions.DatabaseException
import com.tm.transfer.domain.repositories.TransferRepository
import com.tm.transfer.domain.TransferData
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

@Component
class TransferSql(
    private val repository: TransferRepositoryJPA
) : TransferRepository {

    override fun save(transferData: TransferData) : TransferData {
        try {
            val entity = transferData.toEntity()

            repository.save(entity)

            return transferData.copy(transferId = entity.transferId, createdAt = entity.createdAt)
        } catch (ex: Exception) {
            throw DatabaseException("Error when try create a new transfer in DB.", ex)
        }
    }

    override fun findByShippingAccount(shippingAccount: String): List<TransferData>? {
        TODO("Not yet implemented")
    }

//    override fun findAllByShippingAccount(shippingAccount: String)  {
//        val result = repository.findAllByShippingAccount(shippingAccount)
//
//        result.map { it.toDomain() }
//    }
}

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

@Repository
interface TransferRepositoryJPA : CrudRepository<TransferDataEntity, String> {

    fun findAllByShippingAccount(shippingAccount: String) : Optional<List<TransferDataEntity>>
}

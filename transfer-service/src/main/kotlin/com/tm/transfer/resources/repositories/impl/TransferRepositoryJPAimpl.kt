package com.tm.transfer.resources.repositories.impl

import com.tm.transfer.application.exceptions.DatabaseException
import com.tm.transfer.domain.repositories.TransferRepository
import com.tm.transfer.resources.repositories.TransferDataEntity
import com.tm.transfer.resources.repositories.TransferRepositoryJPA
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class TransferSql: TransferRepository {

    @Autowired
    private lateinit var repository: TransferRepositoryJPA

    override fun save(transferDataEntity: TransferDataEntity) : TransferDataEntity {
        try {
            return repository.save(transferDataEntity)
        } catch (ex: Exception) {
            throw DatabaseException("Error when try create a new transfer in DB.", ex)
        }
    }

    override fun findAllByShippingAccount(shippingAccount: String): List<TransferDataEntity> {
        return repository.findAllByShippingAccount(shippingAccount)
    }

}

package com.tm.transfer.domain.services

import com.tm.transfer.application.exceptions.InsufficientBalanceException
import com.tm.transfer.domain.TransferData
import com.tm.transfer.domain.repositories.TransferRepository
import com.tm.transfer.domain.toEntity
import com.tm.transfer.domain.utils.calculatedTax
import com.tm.transfer.resources.repositories.toDomain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.logging.Logger

@Service
class TransferService{
    @Autowired
    private lateinit var repository: TransferRepository

    fun createTransfer(transferData: TransferData) : TransferData {
        logger.info("Starting transfer from account " +
                "${transferData.shippingAccount} to ${transferData.destinationAccount}"
        )

        val balance = getBalance(transferData.shippingAccount)
        val valueWithTax = calculatedTax(transferData.taxType, transferData.transactionValue, transferData.scheduleDate)

        if (valueWithTax > balance) {
            throw InsufficientBalanceException("Insufficient balance, valueWithTax: $valueWithTax")
        }

//        Duvidas da regra de transferias futuras:
//        Para transferencias futuras devo realizar o bloqueio do dinheiro do cliente ou retirar?
//        Teria um serviço de rotina para transferencia de transações com data futura?
//
//        if(transferSaved.scheduleDate.isEqual(LocalDate.now())) {
//          withdrawValueFromShippingAccount(transferSaved.shippingAccount, valueWithTax)
//          addValueDestinationAccount(transferSaved.destinationAccount, transferSaved.transactionValue)
//        }

        val transaction = repository.save(transferData.copy(valueWithTax = valueWithTax).toEntity())

        logger.info("Finished transfer to account ${transaction.destinationAccount} " +
                "with id: ${transaction.transferId}")

        return transaction.toDomain()
    }

    fun findByShippingAccount(shippingAccount: String) : List<TransferData> {
        logger.info("Searching transaction for shippingAccount: $shippingAccount")
        val transactions = repository.findAllByShippingAccount(shippingAccount)

        logger.info("Total transaction founded for shippingAccount ($shippingAccount): ${transactions.size}")
        return transactions.map { it.toDomain() }
    }

    companion object{
        private val logger = Logger.getLogger(TransferService::class.java.name)
    }
}

fun calculateDaysDifBetweenDates(startDate: LocalDate, finishDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(startDate, finishDate)
}

// Is this service responsible for viewing the balance? If not create a gateway fold in resources.
fun getBalance(shippingAccount: Long): Double {
    return 1000.0
}

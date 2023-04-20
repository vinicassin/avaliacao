package com.tm.transfer.domain.services

import com.tm.transfer.application.exceptions.InsufficientBalanceException
import com.tm.transfer.application.exceptions.InternalException
import com.tm.transfer.application.exceptions.InvalidDateScheduledException
import com.tm.transfer.application.exceptions.ResourceNotFoundException
import com.tm.transfer.domain.TransferData
import com.tm.transfer.domain.repositories.TransferRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class TransferService(
    private val repository: TransferRepository
) {

    fun createTransfer(transferData: TransferData) : TransferData {
        val balance = getBalance(transferData.shippingAccount)
        val valueWithTax = calculatedTax(transferData.taxType, transferData.transactionValue, transferData.scheduleDate)

        if (valueWithTax >= balance) {
            throw InsufficientBalanceException("Insufficient balance, valueWithTax: $valueWithTax")
        }

//        Duvidas da regra de transferias futuras:
//        Para transferencias futuras devo realizar um bloqueio do dinheiro do cliente ou retirar?
//        Teria um serviço de rotina para transferencia de transações com data futura?
//
//        if(transferSaved.scheduleDate.isAfter(LocalDate.now())) {
//            transactionScheduler(
//                transferSaved.shippingAccount,
//                transferSaved.destinationAccount,
//                transferSaved.transferId
//            )
//        }
//        withdrawValueFromShippingAccount(transferSaved.shippingAccount, valueWithTax)
//        addValueDestinationAccount(transferSaved.destinationAccount, transferSaved.transactionValue)

        return repository.save(transferData.copy(valueWithTax = valueWithTax))
    }

    fun findByShippingAccount(shippingAccount: String) {
        repository.findByShippingAccount(shippingAccount)
            ?: throw ResourceNotFoundException("Not found transactions with shippingAccount: $shippingAccount")
    }
}

fun calculatedTax(taxType: String, value: Double, scheduledDate: LocalDate) : Double {
    return when (taxType) {
        "A" -> calculateTaxA(value, scheduledDate)
        "B" -> calculateTaxB(value, scheduledDate)
        "C" -> calculateTaxC(value, scheduledDate)
        "D" -> calculateTaxD(value, scheduledDate)
        else -> {
            throw InternalException("Something wrong when try to calculated tax")
        }
    }
}

fun calculateDaysDifBetweenDates(startDate: LocalDate, finishDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(startDate, finishDate)
}

fun calculateTaxA(value: Double, scheduledDate: LocalDate) : Double {
    if (!scheduledDate.isEqual(LocalDate.now())) {
        throw InvalidDateScheduledException("Error when calculate tax A, only accepted current date.")
    }
    return (value * 3.0.toPercentage()) + 3 + value
}

fun calculateTaxB(value: Double, scheduledDate: LocalDate) : Double {
    val daysUntilSchedule = calculateDaysDifBetweenDates(startDate = LocalDate.now(), finishDate = scheduledDate)
    if (daysUntilSchedule !in 1..10) {
        throw InvalidDateScheduledException("Error when calculate tax, " +
                "invalid date range. Accepted days: 1 day after current date to 10 days.")
    }
    return 12 + value
}

fun calculateTaxC(value: Double, scheduledDate: LocalDate) : Double {
    val daysUntilSchedule = calculateDaysDifBetweenDates(startDate = LocalDate.now(), finishDate = scheduledDate)

    return when (daysUntilSchedule) {
        in 11..20 -> value * regressiveTaxes[0].toPercentage() + value
        in 21..30 -> value * regressiveTaxes[1].toPercentage() + value
        in 31..40 -> value * regressiveTaxes[2].toPercentage() + value
        in 41..Long.MAX_VALUE -> value * regressiveTaxes[3].toPercentage() + value
        else -> {
            throw InvalidDateScheduledException("Error when calculate tax, " +
                    "invalid date range. Accepted just 11 days after current date or more.")
        }
    }
}

fun calculateTaxD(value: Double, scheduledDate: LocalDate) : Double {
    return when (value) {
        in 0.0..1000.0 -> calculateTaxA(value, scheduledDate)
        in 1001.0..2000.0 -> calculateTaxB(value, scheduledDate)
        in 2001.0..Double.POSITIVE_INFINITY -> calculateTaxC(value, scheduledDate)
        else -> {
            throw InternalException("Something wrong when calculated tax D.")
        }
    }
}

fun Double.toPercentage() = this/100

val regressiveTaxes = listOf(8.2, 6.9, 4.7, 1.7)

// Get balance in this service or create a gateway fold in resource and get balance
fun getBalance(shippingAccount: Long): Double {
    return 200_000.0
}

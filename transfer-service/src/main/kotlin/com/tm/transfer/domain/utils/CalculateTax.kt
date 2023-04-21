package com.tm.transfer.domain.utils

import com.tm.transfer.application.exceptions.InternalException
import com.tm.transfer.application.exceptions.InvalidDateScheduledException
import com.tm.transfer.domain.extensions.toPercentage
import com.tm.transfer.domain.services.calculateDaysDifBetweenDates
import java.time.LocalDate

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

val regressiveTaxes = listOf(8.2, 6.9, 4.7, 1.7)
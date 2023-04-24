package com.tm.transfer.domain.extensions

import java.math.RoundingMode

fun Double.toPercentage() = this/100

fun Double.setRoundTwoPlaces() = this.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble()

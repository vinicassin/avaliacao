package com.tm.transfer.application.web.controllers

import com.tm.transfer.application.web.entities.requests.TransferRequest
import com.tm.transfer.application.web.entities.responses.TransferResponse
import com.tm.transfer.domain.TransferData
import com.tm.transfer.domain.TransferService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/transfer")
class TransferController(
    private val service: TransferService
) {

    @PostMapping
    fun post(@RequestBody request: TransferRequest): ResponseEntity<TransferResponse> {

        // Validar se valor é maior que 0 e data de agendamento não é inferior ao dia de hj
        // Não esquecer de refatorar as funções para suas respectivas pastas/responsabilidades

        val domain = request.toDomain()
        val response = service.createTransfer(domain).toResponse()

        return ResponseEntity(response, HttpStatus.CREATED)

    }
}

fun TransferRequest.toDomain() = TransferData(
    shippingAccount = this.shippingAccount,
    destinationAccount = this.destinationAccount,
    valueTransfered = this.value,
    scheduleDate = this.scheduleDate,
    transferDate = LocalDate.now(),
)

fun TransferData.toResponse() = TransferResponse(
    transferId = this.transferId!!,
    shippingAccount = this.shippingAccount,
    destinationAccount = this.destinationAccount,
    valueTransfered = this.valueTransfered,
    valueWithTax = this.valueWithTax!!,
    scheduleDate = this.scheduleDate,
    transferDate = this.transferDate,
    createdAt = this.createdAt!!,
)
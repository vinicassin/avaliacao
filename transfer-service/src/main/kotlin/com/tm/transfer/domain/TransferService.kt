package com.tm.transfer.domain

import org.springframework.stereotype.Service

@Service
class TransferService(
    private val repository: TransferRepository
) {

    fun createTransfer(transferData: TransferData) : TransferData {
        // calculate tax
        val taxCalculated = 2.0

        val valueWithTax = transferData.valueTransfered + taxCalculated

        return repository.save(transferData.copy(valueWithTax = valueWithTax))
    }
}

// A -- mesmo dia -- taxa de 3 reais + 3% do valor
// B -- 2 a 10 dias -- taxa de 12 reais
// C -- 11 a 20 dias -- 8.2% valor total
// C -- 21 a 30 dias -- 6.9%
// C -- 31 a 40 dias -- 4.7%
// C -- 41 dias ou mais -- 1.7%
// D -- Até 1000 reais      - TAXA TIPO A
// D -- Até 1001~2000 reais - TAXA TIPO B
// D -- Até 2001 ou mais    - TAXA TIPO C


// Listar agendamentos por conta bancaria


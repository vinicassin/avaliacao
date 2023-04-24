package com.tm.transfer.application.web.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.tm.transfer.builders.buildTransferDataEntity
import com.tm.transfer.builders.builderTransferRequest
import com.tm.transfer.domain.repositories.TransferRepository
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
class TransferControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var transferDataRepository: TransferRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `Should return list of transfer when call GET by shippingAccount`() {
        val transferDataEntity = buildTransferDataEntity(valueWithTax = 1033.0)
        transferDataRepository.save(transferDataEntity)

        mockMvc.perform(get("/transfer/12345"))
            .andExpect(status().isAccepted)
            .andExpect(jsonPath("$[0].transferId").value(transferDataEntity.transferId))
            .andExpect(jsonPath("$[0].transactionValue").value(transferDataEntity.transactionValue))
            .andExpect(jsonPath("$[0].valueWithTax").value(transferDataEntity.valueWithTax))
    }

    @Test
    fun `Should create transfer succesfully`() {
        val request = builderTransferRequest()

        mockMvc.perform(post("/transfer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated)

        val result = transferDataRepository.findAllByShippingAccount("123456")

        assertEquals(result[0].valueWithTax, 1033.0)
    }
}

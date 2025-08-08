package com.wefin.srm.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefin.srm.api.dto.ConversionRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import com.wefin.srm.domain.service.TransactionService;
import com.wefin.srm.config.SecurityConfig;
import com.wefin.srm.config.OpenApiConfig;
import com.wefin.srm.api.dto.TransactionResponseDto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class) @Import({SecurityConfig.class, OpenApiConfig.class})
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    void performExchangeShouldReturn200WhenRequestIsValid() throws Exception {
        ConversionRequestDto requestDto = new ConversionRequestDto();
        requestDto.setProductId(1L);
        requestDto.setAmount(new BigDecimal("100.0"));
        requestDto.setFromCurrencyCode("GOL");
        requestDto.setToCurrencyCode("TIB");

        TransactionResponseDto responseDto = new TransactionResponseDto();

        given(transactionService.executeExchange(any(ConversionRequestDto.class))).willReturn(responseDto);

        mockMvc.perform(post("/api/v1/transactions/exchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }
}

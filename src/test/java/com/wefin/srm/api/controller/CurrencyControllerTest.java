package com.wefin.srm.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wefin.srm.api.dto.ExchangeRateDto;
import com.wefin.srm.api.exception.ResourceNotFoundException;
import com.wefin.srm.config.OpenApiConfig;
import com.wefin.srm.config.SecurityConfig;
import com.wefin.srm.domain.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
@Import({SecurityConfig.class, OpenApiConfig.class})
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getRatehouldReturn200AndExchangeRateWhenRateExists() throws Exception {
        String fromCode = "GOL";
        String toCode = "TIB";
        ExchangeRateDto expectedDto = new ExchangeRateDto(fromCode, toCode, new BigDecimal("2.5"), LocalDate.now());

        given(currencyService.getMostRecentRate(fromCode, toCode)).willReturn(expectedDto);

        mockMvc.perform(get("/api/v1/rates")
                        .param("from", fromCode)
                        .param("to", toCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCurrency").value(fromCode))
                .andExpect(jsonPath("$.toCurrency").value(toCode))
                .andExpect(jsonPath("$.rate").value(2.5));
    }

    @Test
    void getRateShouldReturn404WhenRateNotFound() throws Exception {
        String fromCode = "ABC";
        String toCode = "XYZ";
        String errorMessage = String.format("Exchange rate from %s to %s not found.", fromCode, toCode);

        given(currencyService.getMostRecentRate(fromCode, toCode)).willThrow(new ResourceNotFoundException(errorMessage));

        mockMvc.perform(get("/api/v1/rates")
                        .param("from", fromCode)
                        .param("to", toCode))
                .andExpect(status().isNotFound());
    }
}

package com.wefin.srm.api.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ExchangeRateUpdateDto {
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private BigDecimal rate;
}
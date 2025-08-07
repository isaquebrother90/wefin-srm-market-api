package com.wefin.srm.domain.service.conversion;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component("RARE_SKIN")
public class RareSkinConversionStrategy implements ConversionStrategy {
    private static final BigDecimal PROTECTION_TAX = new BigDecimal("0.95"); // 5% de taxa
    @Override
    public BigDecimal calculate(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).multiply(PROTECTION_TAX);
    }
}
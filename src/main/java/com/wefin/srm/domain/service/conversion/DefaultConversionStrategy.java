package com.wefin.srm.domain.service.conversion;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component("DEFAULT")
public class DefaultConversionStrategy implements ConversionStrategy {

    @Override
    public BigDecimal calculate(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate);
    }
}
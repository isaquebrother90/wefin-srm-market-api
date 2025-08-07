package com.wefin.srm.domain.service.conversion;

import java.math.BigDecimal;

public interface ConversionStrategy {
    BigDecimal calculate(BigDecimal amount, BigDecimal rate);
}

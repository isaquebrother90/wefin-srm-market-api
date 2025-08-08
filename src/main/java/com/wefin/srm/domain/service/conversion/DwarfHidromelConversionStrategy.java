package com.wefin.srm.domain.service.conversion;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component("DWARF_HIDROMEL")
public class DwarfHidromelConversionStrategy implements ConversionStrategy {
    private static final BigDecimal BONUS = new BigDecimal("1.10"); // 10% bônus

    @Override
    public BigDecimal calculate(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).multiply(BONUS);
    }
}

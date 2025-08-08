package com.wefin.srm.domain.service.conversion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ConversionStrategyTest {

    private static Stream<ConversionStrategy> standardStrategyProvider() {
        return Stream.of(new DefaultConversionStrategy());
    }

    @ParameterizedTest(name = "Teste para a estratégia padrão: {0}")
    @MethodSource("standardStrategyProvider")
    void calculateShouldWorkForStandardStrategy(ConversionStrategy strategy) {
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal rate = new BigDecimal("2.5");
        BigDecimal expectedResult = new BigDecimal("250.00");

        BigDecimal actualResult = strategy.calculate(amount, rate);

        assertThat(actualResult.setScale(2, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedResult.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("Deve aplicar a sobretaxa de 10% para a estratégia DwarfHidromel")
    void calculateShouldApplySurchargeForDwarfHidromelStrategy() {
        ConversionStrategy strategy = new DwarfHidromelConversionStrategy();
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal rate = new BigDecimal("2.5");
        BigDecimal expectedResult = new BigDecimal("275.00");

        BigDecimal actualResult = strategy.calculate(amount, rate);

        assertThat(actualResult.setScale(2, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedResult.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("Deve aplicar a taxa de proteção de 5% para a estratégia RareSkin")
    void calculateShouldApplyProtectionTaxForRareSkinStrategy() {
        ConversionStrategy strategy = new RareSkinConversionStrategy();
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal rate = new BigDecimal("2.5");
        BigDecimal expectedResult = new BigDecimal("237.50");

        BigDecimal actualResult = strategy.calculate(amount, rate);

        assertThat(actualResult.setScale(2, RoundingMode.HALF_UP))
                .isEqualByComparingTo(expectedResult.setScale(2, RoundingMode.HALF_UP));
    }
}

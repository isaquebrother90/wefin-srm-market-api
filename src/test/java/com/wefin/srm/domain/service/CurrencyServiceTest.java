package com.wefin.srm.domain.service;

import com.wefin.srm.api.dto.ExchangeRateUpdateDto;
import com.wefin.srm.api.exception.ResourceNotFoundException;
import com.wefin.srm.domain.model.Currency;
import com.wefin.srm.domain.repository.CurrencyRepository;
import com.wefin.srm.domain.repository.ExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyService currencyService;

    @Test
    void getMostRecentRateShouldThrowExceptionWhenRateNotFound() {
        String fromCode = "GOL";
        String toCode = "TIB";
        given(exchangeRateRepository.findTopByFromCurrencyCodeAndToCurrencyCodeOrderByEffectiveDateDescIdDesc(fromCode, toCode))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> currencyService.getMostRecentRate(fromCode, toCode))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Exchange rate from GOL to TIB not found.");
    }

    @Test
    void updateRateShouldThrowExceptionWhenFromCurrencyNotFound() {
        ExchangeRateUpdateDto dto = new ExchangeRateUpdateDto("XYZ", "TIB", new BigDecimal("3.0"));

        given(currencyRepository.findByCode("XYZ")).willReturn(Optional.empty());

        assertThatThrownBy(() -> currencyService.updateRate(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Currency not found: " + dto.getFromCurrencyCode());

        verify(currencyRepository, never()).findByCode("TIB");
        verify(exchangeRateRepository, never()).save(any());
    }

    @Test
    void updateRateShouldThrowExceptionWhenToCurrencyNotFound() {
        ExchangeRateUpdateDto dto = new ExchangeRateUpdateDto("GOL", "ABC", new BigDecimal("3.0"));
        Currency fromCurrency = new Currency(1L, "Ouro Real", "GOL");

        given(currencyRepository.findByCode("GOL")).willReturn(Optional.of(fromCurrency));
        given(currencyRepository.findByCode("ABC")).willReturn(Optional.empty());

        assertThatThrownBy(() -> currencyService.updateRate(dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Currency not found: " + dto.getToCurrencyCode());

        verify(exchangeRateRepository, never()).save(any());
    }
}

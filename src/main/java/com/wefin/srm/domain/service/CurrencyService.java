package com.wefin.srm.domain.service;

import com.wefin.srm.api.dto.ExchangeRateDto;
import com.wefin.srm.api.dto.ExchangeRateUpdateDto;
import com.wefin.srm.api.exception.ResourceNotFoundException;
import com.wefin.srm.domain.model.Currency;
import com.wefin.srm.domain.model.ExchangeRate;
import com.wefin.srm.domain.repository.CurrencyRepository;
import com.wefin.srm.domain.repository.ExchangeRateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CurrencyService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public CurrencyService(ExchangeRateRepository exchangeRateRepository, CurrencyRepository currencyRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    public ExchangeRateDto updateRate(ExchangeRateUpdateDto dto) {
        Currency fromCurrency = currencyRepository.findByCode(dto.getFromCurrencyCode())
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found: " + dto.getFromCurrencyCode()));

        Currency toCurrency = currencyRepository.findByCode(dto.getToCurrencyCode())
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found: " + dto.getToCurrencyCode()));

        ExchangeRate newRate = new ExchangeRate();
        newRate.setFromCurrency(fromCurrency);
        newRate.setToCurrency(toCurrency);
        newRate.setRate(dto.getRate());
        newRate.setEffectiveDate(LocalDate.now());

        exchangeRateRepository.save(newRate);

        return toDto(newRate);
    }

    public ExchangeRateDto getMostRecentRate(String fromCurrencyCode, String toCurrencyCode) {
        ExchangeRate rate =
                exchangeRateRepository.findTopByFromCurrencyCodeAndToCurrencyCodeOrderByEffectiveDateDescIdDesc(fromCurrencyCode, toCurrencyCode)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Exchange rate from %s to %s not found.", fromCurrencyCode, toCurrencyCode)));
        return toDto(rate);
    }

    private ExchangeRateDto toDto(ExchangeRate rate) {
        return new ExchangeRateDto(
                rate.getFromCurrency().getCode(),
                rate.getToCurrency().getCode(),
                rate.getRate(),
                rate.getEffectiveDate()
        );
    }

}

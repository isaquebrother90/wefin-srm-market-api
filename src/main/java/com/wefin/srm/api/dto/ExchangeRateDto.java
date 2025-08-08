package com.wefin.srm.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ExchangeRateDto {
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal rate;
    private LocalDate effectiveDate;

    public ExchangeRateDto() {
    }

    public ExchangeRateDto(String fromCurrency, String toCurrency, BigDecimal rate, LocalDate effectiveDate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
        this.effectiveDate = effectiveDate;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "ExchangeRateDto{" +
                "fromCurrency='" + fromCurrency + '\'' +
                ", toCurrency='" + toCurrency + '\'' +
                ", rate=" + rate +
                ", effectiveDate=" + effectiveDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateDto that = (ExchangeRateDto) o;
        return Objects.equals(fromCurrency, that.fromCurrency) && Objects.equals(toCurrency, that.toCurrency) && Objects.equals(rate, that.rate) && Objects.equals(effectiveDate, that.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCurrency, toCurrency, rate, effectiveDate);
    }
}

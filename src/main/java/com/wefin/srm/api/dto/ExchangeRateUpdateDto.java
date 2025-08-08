package com.wefin.srm.api.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeRateUpdateDto {
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private BigDecimal rate;

    public ExchangeRateUpdateDto(String fromCurrencyCode, String toCurrencyCode, BigDecimal rate) {
        this.fromCurrencyCode = fromCurrencyCode;
        this.toCurrencyCode = toCurrencyCode;
        this.rate = rate;
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public void setFromCurrencyCode(String fromCurrencyCode) {
        this.fromCurrencyCode = fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public void setToCurrencyCode(String toCurrencyCode) {
        this.toCurrencyCode = toCurrencyCode;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "ExchangeRateUpdateDto{" +
                "fromCurrencyCode='" + fromCurrencyCode + '\'' +
                ", toCurrencyCode='" + toCurrencyCode + '\'' +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateUpdateDto that = (ExchangeRateUpdateDto) o;
        return Objects.equals(fromCurrencyCode, that.fromCurrencyCode) && Objects.equals(toCurrencyCode, that.toCurrencyCode) && Objects.equals(rate, that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCurrencyCode, toCurrencyCode, rate);
    }
}
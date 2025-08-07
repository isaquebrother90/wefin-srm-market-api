package com.wefin.srm.api.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class  ConversionRequestDto {
    private Long productId;
    private BigDecimal amount;
    private String fromCurrencyCode;
    private String toCurrencyCode;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return "ConversionRequestDto{" +
                "productId=" + productId +
                ", amount=" + amount +
                ", fromCurrencyCode='" + fromCurrencyCode + '\'' +
                ", toCurrencyCode='" + toCurrencyCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ConversionRequestDto that = (ConversionRequestDto) o;
        return Objects.equals(productId, that.productId) && Objects.equals(amount, that.amount) && Objects.equals(fromCurrencyCode, that.fromCurrencyCode) && Objects.equals(toCurrencyCode, that.toCurrencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, amount, fromCurrencyCode, toCurrencyCode);
    }
}


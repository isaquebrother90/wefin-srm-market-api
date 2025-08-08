package com.wefin.srm.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class TransactionResponseDto {
    private Long transactionId;
    private String productName;
    private BigDecimal originalAmount;
    private String originalCurrency;
    private BigDecimal convertedAmount;
    private String destinationCurrency;
    private BigDecimal rateUsed;
    private LocalDateTime transactionDate;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(Long transactionId, String productName, BigDecimal originalAmount, String originalCurrency, BigDecimal convertedAmount, String destinationCurrency, BigDecimal rateUsed, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.productName = productName;
        this.originalAmount = originalAmount;
        this.originalCurrency = originalCurrency;
        this.convertedAmount = convertedAmount;
        this.destinationCurrency = destinationCurrency;
        this.rateUsed = rateUsed;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(String originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public String getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(String destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public BigDecimal getRateUsed() {
        return rateUsed;
    }

    public void setRateUsed(BigDecimal rateUsed) {
        this.rateUsed = rateUsed;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "TransactionResponseDto{" +
                "transactionId=" + transactionId +
                ", productName='" + productName + '\'' +
                ", originalAmount=" + originalAmount +
                ", originalCurrency='" + originalCurrency + '\'' +
                ", convertedAmount=" + convertedAmount +
                ", destinationCurrency='" + destinationCurrency + '\'' +
                ", rateUsed=" + rateUsed +
                ", transactionDate=" + transactionDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransactionResponseDto that = (TransactionResponseDto) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(productName, that.productName) && Objects.equals(originalAmount, that.originalAmount) && Objects.equals(originalCurrency, that.originalCurrency) && Objects.equals(convertedAmount, that.convertedAmount) && Objects.equals(destinationCurrency, that.destinationCurrency) && Objects.equals(rateUsed, that.rateUsed) && Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, productName, originalAmount, originalCurrency, convertedAmount, destinationCurrency, rateUsed, transactionDate);
    }
}

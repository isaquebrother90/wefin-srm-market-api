package com.wefin.srm.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal originalAmount;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal convertedAmount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "original_currency_id")
    private Currency originalCurrency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_currency_id")
    private Currency destinationCurrency;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(Long id, Product product, BigDecimal originalAmount, BigDecimal convertedAmount, Currency originalCurrency, Currency destinationCurrency, LocalDateTime transactionDate) {
        this.id = id;
        this.product = product;
        this.originalAmount = originalAmount;
        this.convertedAmount = convertedAmount;
        this.originalCurrency = originalCurrency;
        this.destinationCurrency = destinationCurrency;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public Currency getOriginalCurrency() {
        return originalCurrency;
    }

    public void setOriginalCurrency(Currency originalCurrency) {
        this.originalCurrency = originalCurrency;
    }

    public Currency getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(Currency destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", product=" + product +
                ", originalAmount=" + originalAmount +
                ", convertedAmount=" + convertedAmount +
                ", originalCurrency=" + originalCurrency +
                ", destinationCurrency=" + destinationCurrency +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
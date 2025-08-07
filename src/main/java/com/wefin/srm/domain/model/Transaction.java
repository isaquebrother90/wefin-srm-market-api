package com.wefin.srm.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Transaction {

    @EqualsAndHashCode.Include
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
}

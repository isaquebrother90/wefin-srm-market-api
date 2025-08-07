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
import java.time.LocalDate;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ExchangeRate {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "from_currency_id")
    private Currency fromCurrency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "to_currency_id")
    private Currency toCurrency;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal rate;

    @Column(nullable = false)
    private LocalDate effectiveDate;
}

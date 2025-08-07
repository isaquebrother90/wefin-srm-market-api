package com.wefin.srm.domain.repository;

import com.wefin.srm.domain.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    @Query("SELECT er FROM ExchangeRate er " +
            "WHERE er.fromCurrency.code = :fromCurrencyCode AND er.toCurrency.code = :toCurrencyCode " +
            "ORDER BY er.effectiveDate DESC, er.id DESC " +
            "LIMIT 1")
    Optional<ExchangeRate> findMostRecentRate(String fromCurrencyCode, String toCurrencyCode);
}

package com.wefin.srm.domain.repository;

import com.wefin.srm.domain.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findTopByFromCurrencyCodeAndToCurrencyCodeOrderByEffectiveDateDescIdDesc(String fromCurrencyCode, String toCurrencyCode);
}

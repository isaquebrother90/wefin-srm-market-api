package com.wefin.srm.domain.repository;

import com.wefin.srm.domain.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {}

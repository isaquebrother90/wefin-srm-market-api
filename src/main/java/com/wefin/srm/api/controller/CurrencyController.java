package com.wefin.srm.api.controller;

import com.wefin.srm.api.dto.ExchangeRateDto;
import com.wefin.srm.api.dto.ExchangeRateUpdateDto;
import com.wefin.srm.domain.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rates" )
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExchangeRateDto> updateRate(@RequestBody ExchangeRateUpdateDto dto) {
        ExchangeRateDto updatedRate = currencyService.updateRate(dto);
        return ResponseEntity.ok(updatedRate);
    }

    @GetMapping
    public ResponseEntity<ExchangeRateDto> getRate(@RequestParam String from, @RequestParam String to) {
        ExchangeRateDto rate = currencyService.getMostRecentRate(from, to);
        return ResponseEntity.ok(rate);
    }
}

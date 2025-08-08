package com.wefin.srm.api.controller;

import com.wefin.srm.api.dto.ExchangeRateDto;
import com.wefin.srm.api.dto.ExchangeRateUpdateDto;
import com.wefin.srm.domain.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rates" )
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping
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

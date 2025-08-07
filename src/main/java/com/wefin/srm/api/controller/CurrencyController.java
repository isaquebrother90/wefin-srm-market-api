package com.wefin.srm.api.controller;

import com.wefin.srm.api.dto.ExchangeRateDto;
import com.wefin.srm.api.dto.ExchangeRateUpdateDto;
import com.wefin.srm.domain.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rates" )
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

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

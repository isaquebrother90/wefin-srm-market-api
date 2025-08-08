package com.wefin.srm.api.controller;

import com.wefin.srm.api.dto.ConversionRequestDto;
import com.wefin.srm.api.dto.TransactionResponseDto;
import com.wefin.srm.domain.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions" )
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/exchange")
    public ResponseEntity<TransactionResponseDto> performExchange(@RequestBody ConversionRequestDto request) {
        TransactionResponseDto response = transactionService.executeExchange(request);
        return ResponseEntity.ok(response);
    }
}

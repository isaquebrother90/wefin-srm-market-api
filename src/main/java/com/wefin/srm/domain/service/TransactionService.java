package com.wefin.srm.domain.service;

import com.wefin.srm.api.dto.ConversionRequestDto;
import com.wefin.srm.api.dto.TransactionResponseDto;
import com.wefin.srm.api.exception.ResourceNotFoundException;
import com.wefin.srm.domain.model.Currency;
import com.wefin.srm.domain.model.ExchangeRate;
import com.wefin.srm.domain.model.Product;
import com.wefin.srm.domain.model.Transaction;
import com.wefin.srm.domain.repository.CurrencyRepository;
import com.wefin.srm.domain.repository.ExchangeRateRepository;
import com.wefin.srm.domain.repository.ProductRepository;
import com.wefin.srm.domain.repository.TransactionRepository;
import com.wefin.srm.domain.service.conversion.ConversionStrategy;
import com.wefin.srm.domain.service.conversion.ConversionStrategyFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    private final CurrencyRepository currencyRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ConversionStrategyFactory conversionStrategyFactory;

    public TransactionService(TransactionRepository transactionRepository, ProductRepository productRepository, CurrencyRepository currencyRepository,
                              ExchangeRateRepository exchangeRateRepository, ConversionStrategyFactory conversionStrategyFactory) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.currencyRepository = currencyRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.conversionStrategyFactory = conversionStrategyFactory;
    }

    @Transactional
    public TransactionResponseDto executeExchange(ConversionRequestDto request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        Currency fromCurrency = currencyRepository.findByCode(request.getFromCurrencyCode())
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found: " + request.getFromCurrencyCode()));

        Currency toCurrency = currencyRepository.findByCode(request.getToCurrencyCode())
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found: " + request.getToCurrencyCode()));

        //Busca a taxa de câmbio mais recente
        ExchangeRate exchangeRate = exchangeRateRepository.findTopByFromCurrencyCodeAndToCurrencyCodeOrderByEffectiveDateDescIdDesc(request.getFromCurrencyCode(), request.getToCurrencyCode())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Exchange rate from %s to %s not found.", request.getFromCurrencyCode(), request.getToCurrencyCode())));

        ConversionStrategy strategy = conversionStrategyFactory.getStrategy(product);

        //Calcula o valor convertido
        BigDecimal convertedAmount = strategy.calculate(request.getAmount(), exchangeRate.getRate());

        //Criar e salva a transação
        Transaction transaction = new Transaction();
        transaction.setProduct(product);
        transaction.setOriginalAmount(request.getAmount());
        transaction.setOriginalCurrency(fromCurrency);
        transaction.setConvertedAmount(convertedAmount);
        transaction.setDestinationCurrency(toCurrency);
        transaction.setTransactionDate(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return new TransactionResponseDto(
                savedTransaction.getId(),
                product.getName(),
                request.getAmount(),
                fromCurrency.getCode(),
                convertedAmount,
                toCurrency.getCode(),
                exchangeRate.getRate(),
                savedTransaction.getTransactionDate()
        );

    }
}


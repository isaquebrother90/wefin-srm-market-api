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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private ExchangeRateRepository exchangeRateRepository;
    @Mock
    private ConversionStrategyFactory conversionStrategyFactory;
    @Mock
    private ConversionStrategy mockStrategy;

    @InjectMocks
    private TransactionService transactionService;

    private Product product;
    private Currency fromCurrency;
    private Currency toCurrency;
    private ExchangeRate exchangeRate;
    private ConversionRequestDto requestDto;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Hidromel dos Anãos", "Montanhas de Ferro");
        fromCurrency = new Currency(1L, "Ouro Real", "GOL");
        toCurrency = new Currency(2L, "Tibar", "TIB");
        exchangeRate = new ExchangeRate(1L, fromCurrency, toCurrency, new BigDecimal("2.5"), LocalDate.now());

        requestDto = new ConversionRequestDto();
        requestDto.setProductId(1L);
        requestDto.setAmount(new BigDecimal("100.00"));
        requestDto.setFromCurrencyCode("GOL");
        requestDto.setToCurrencyCode("TIB");
    }

    @Test
    @DisplayName("Deve executar a troca com sucesso quando todos os dados são válidos")
    void executeExchangeShouldSucceedWhenAllDataIsValid() {

        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(currencyRepository.findByCode("GOL")).willReturn(Optional.of(fromCurrency));
        given(currencyRepository.findByCode("TIB")).willReturn(Optional.of(toCurrency));
        given(exchangeRateRepository.findTopByFromCurrencyCodeAndToCurrencyCodeOrderByEffectiveDateDescIdDesc("GOL", "TIB"))
                .willReturn(Optional.of(exchangeRate));
        given(conversionStrategyFactory.getStrategy(product)).willReturn(mockStrategy);
        given(mockStrategy.calculate(any(BigDecimal.class), any(BigDecimal.class))).willReturn(new BigDecimal("275.00"));

        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(100L);
        savedTransaction.setTransactionDate(LocalDateTime.now());
        given(transactionRepository.save(any(Transaction.class))).willReturn(savedTransaction);

        TransactionResponseDto response = transactionService.executeExchange(requestDto);

        assertThat(response).isNotNull();
        assertThat(response.getTransactionId()).isEqualTo(100L);
        assertThat(response.getProductName()).isEqualTo("Hidromel dos Anãos");
        assertThat(response.getOriginalAmount()).isEqualByComparingTo("100.00");
        assertThat(response.getConvertedAmount()).isEqualByComparingTo("275.00");
        assertThat(response.getRateUsed()).isEqualByComparingTo("2.5");

        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void executeExchangeShouldThrowExceptionWhenProductNotFound() {
        given(productRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.executeExchange(requestDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with id: 1");
    }

    @Test
    void executeExchangeShouldThrowExceptionWhenFromCurrencyNotFound() {
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(currencyRepository.findByCode("GOL")).willReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.executeExchange(requestDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Currency not found: GOL");
    }

    @Test
    void executeExchangeShouldThrowExceptionWhenToCurrencyNotFound() {
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(currencyRepository.findByCode("GOL")).willReturn(Optional.of(fromCurrency));
        given(currencyRepository.findByCode("TIB")).willReturn(Optional.empty());


        assertThatThrownBy(() -> transactionService.executeExchange(requestDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Currency not found: TIB");
    }

    @Test
    void executeExchangeShouldThrowExceptionWhenExchangeRateNotFound() {
        given(productRepository.findById(1L)).willReturn(Optional.of(product));
        given(currencyRepository.findByCode("GOL")).willReturn(Optional.of(fromCurrency));
        given(currencyRepository.findByCode("TIB")).willReturn(Optional.of(toCurrency));
        given(exchangeRateRepository.findTopByFromCurrencyCodeAndToCurrencyCodeOrderByEffectiveDateDescIdDesc(anyString(), anyString()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> transactionService.executeExchange(requestDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Exchange rate from GOL to TIB not found.");
    }
}

package com.wefin.srm.domain.service.conversion;

import com.wefin.srm.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ConversionStrategyFactoryTest {

    @Mock
    private DefaultConversionStrategy defaultStrategy;
    @Mock
    private DwarfHidromelConversionStrategy dwarfStrategy;
    @Mock
    private RareSkinConversionStrategy rareSkinStrategy;

    private ConversionStrategyFactory factory;

    @BeforeEach
    void setUp() {
        Map<String, ConversionStrategy> strategyMap = new HashMap<>();
        strategyMap.put("DEFAULT", defaultStrategy);
        strategyMap.put("DWARF_HIDROMEL", dwarfStrategy);
        strategyMap.put("RARE_SKIN", rareSkinStrategy);

        factory = new ConversionStrategyFactory(strategyMap);
    }

    @Test
    void shouldReturnDwarfStrategyWhenProductIsDwarfHidromel() {
        Product hidromel = new Product();
        hidromel.setName("Hidromel dos Anãos");
        hidromel.setOriginRealm("Montanhas de Ferro");

        ConversionStrategy result = factory.getStrategy(hidromel);

        assertThat(result).isEqualTo(dwarfStrategy);
    }

    @Test
    void shouldReturnRareSkinStrategyWhenProductIsRareSkin() {
        Product peles = new Product();
        peles.setName("Peles Raras");
        peles.setOriginRealm("Qualquer Reino");

        ConversionStrategy result = factory.getStrategy(peles);

        assertThat(result).isEqualTo(rareSkinStrategy);
    }

    @Test
    void shouldReturnDefaultStrategyWhenProductIsCommon() {
        Product madeira = new Product();
        madeira.setName("Madeira de Carvalho");
        madeira.setOriginRealm("Floresta de Wefin");

        ConversionStrategy result = factory.getStrategy(madeira);

        assertThat(result).isEqualTo(defaultStrategy);
    }

    @Test
    void shouldReturnDefaultStrategyWhenProductIsNull() {
        Product product = null;
        ConversionStrategy result = factory.getStrategy(product);

        assertThat(result).isEqualTo(defaultStrategy);
    }
}
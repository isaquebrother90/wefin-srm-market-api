package com.wefin.srm.domain.service.conversion;

import com.wefin.srm.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ConversionStrategyFactory {

    private final Map<String, ConversionStrategy> strategies;

    public ConversionStrategy getStrategy(Product product) {
        if ("Hidromel dos Anãos".equals(product.getName()) && "Montanhas de Ferro".equals(product.getOriginRealm())) {
            return strategies.get("DWARF_HIDROMEL");
        }
        if ("Peles Raras".equals(product.getName())) {
            return strategies.get("RARE_SKIN");
        }
        return strategies.get("DEFAULT");
    }
}
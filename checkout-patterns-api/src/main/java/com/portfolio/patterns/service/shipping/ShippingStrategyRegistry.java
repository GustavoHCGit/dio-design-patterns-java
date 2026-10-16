package com.portfolio.patterns.service.shipping;

import com.portfolio.patterns.domain.ShippingMethod;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class ShippingStrategyRegistry {
    private final Map<ShippingMethod, ShippingStrategy> strategies;

    public ShippingStrategyRegistry(List<ShippingStrategy> strategies) {
        Map<ShippingMethod, ShippingStrategy> indexed = new EnumMap<>(ShippingMethod.class);
        strategies.forEach(strategy -> {
            ShippingStrategy previous = indexed.put(strategy.method(), strategy);
            if (previous != null) {
                throw new IllegalStateException("Mais de uma estratégia de envio para " + strategy.method());
            }
        });
        this.strategies = Map.copyOf(indexed);
    }

    public ShippingStrategy forMethod(ShippingMethod method) {
        ShippingStrategy strategy = strategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Nenhuma estratégia de envio registrada para " + method);
        }
        return strategy;
    }
}

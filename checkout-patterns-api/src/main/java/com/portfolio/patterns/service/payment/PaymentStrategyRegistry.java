package com.portfolio.patterns.service.payment;

import com.portfolio.patterns.domain.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentStrategyRegistry {
    private final Map<PaymentMethod, PaymentStrategy> strategies;

    public PaymentStrategyRegistry(List<PaymentStrategy> strategies) {
        Map<PaymentMethod, PaymentStrategy> indexed = new EnumMap<>(PaymentMethod.class);
        strategies.forEach(strategy -> {
            PaymentStrategy previous = indexed.put(strategy.method(), strategy);
            if (previous != null) {
                throw new IllegalStateException("Mais de uma estratégia para " + strategy.method());
            }
        });
        this.strategies = Map.copyOf(indexed);
    }

    public PaymentStrategy forMethod(PaymentMethod method) {
        PaymentStrategy strategy = strategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Nenhuma estratégia de pagamento registrada para " + method);
        }
        return strategy;
    }
}

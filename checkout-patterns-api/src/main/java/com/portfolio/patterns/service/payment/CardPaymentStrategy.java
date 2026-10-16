package com.portfolio.patterns.service.payment;

import com.portfolio.patterns.domain.PaymentMethod;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public PaymentMethod method() { return PaymentMethod.CARD; }

    @Override
    public String authorize(BigDecimal amount) {
        return "SIMULATED-CARD-" + UUID.randomUUID();
    }
}

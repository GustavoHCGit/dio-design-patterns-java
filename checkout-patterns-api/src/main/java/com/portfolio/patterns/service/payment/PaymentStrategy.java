package com.portfolio.patterns.service.payment;

import com.portfolio.patterns.domain.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentStrategy {
    PaymentMethod method();
    String authorize(BigDecimal amount);
}

package com.portfolio.patterns.service.shipping;

import com.portfolio.patterns.domain.ShippingMethod;

import java.math.BigDecimal;

public interface ShippingStrategy {
    ShippingMethod method();
    BigDecimal calculate(BigDecimal subtotal);
}

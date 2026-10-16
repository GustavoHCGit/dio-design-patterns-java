package com.portfolio.patterns.service.shipping;

import com.portfolio.patterns.domain.ShippingMethod;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StandardShippingStrategy implements ShippingStrategy {
    @Override
    public ShippingMethod method() { return ShippingMethod.STANDARD; }

    @Override
    public BigDecimal calculate(BigDecimal subtotal) { return new BigDecimal("6.90"); }
}

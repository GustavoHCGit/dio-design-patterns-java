package com.portfolio.patterns.validation;

import com.portfolio.patterns.api.CreateOrderRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckoutValidationChain {
    private final List<OrderRequestValidator> validators;

    public CheckoutValidationChain(List<OrderRequestValidator> validators) {
        this.validators = validators;
    }

    public void validate(CreateOrderRequest request) {
        validators.forEach(validator -> validator.validate(request));
    }
}

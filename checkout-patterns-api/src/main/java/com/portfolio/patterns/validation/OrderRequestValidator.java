package com.portfolio.patterns.validation;

import com.portfolio.patterns.api.CreateOrderRequest;

public interface OrderRequestValidator {
    void validate(CreateOrderRequest request);
}

package com.portfolio.patterns.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrderItemRequest(
        @NotBlank String productCode,
        @Positive int quantity) {
}

package com.portfolio.patterns.validation;

import com.portfolio.patterns.api.CreateOrderRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
public class ItemLimitValidator implements OrderRequestValidator {
    private static final int MAX_DISTINCT_ITEMS = 10;
    private static final int MAX_QUANTITY_PER_ITEM = 20;

    @Override
    public void validate(CreateOrderRequest request) {
        if (request.items().size() > MAX_DISTINCT_ITEMS) {
            throw new IllegalArgumentException("O pedido pode conter no máximo 10 itens distintos.");
        }
        if (request.items().stream().anyMatch(item -> item.quantity() > MAX_QUANTITY_PER_ITEM)) {
            throw new IllegalArgumentException("A quantidade máxima por produto é 20.");
        }
    }
}

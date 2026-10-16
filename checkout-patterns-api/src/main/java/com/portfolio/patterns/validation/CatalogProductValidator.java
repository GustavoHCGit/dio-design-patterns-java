package com.portfolio.patterns.validation;

import com.portfolio.patterns.api.CreateOrderRequest;
import com.portfolio.patterns.service.ProductCatalog;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(20)
public class CatalogProductValidator implements OrderRequestValidator {
    private final ProductCatalog catalog;

    public CatalogProductValidator(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void validate(CreateOrderRequest request) {
        request.items().forEach(item -> catalog.findByCode(item.productCode()));
    }
}

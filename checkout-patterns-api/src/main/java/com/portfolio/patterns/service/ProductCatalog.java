package com.portfolio.patterns.service;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class ProductCatalog {
    private final Map<String, Product> products = Map.of(
            "JAVA-CLEAN-CODE", new Product("JAVA-CLEAN-CODE", "Java: Clean Code", new BigDecimal("59.90")),
            "GOF-PATTERNS", new Product("GOF-PATTERNS", "Padrões GoF na Prática", new BigDecimal("49.90")),
            "SPRING-REST", new Product("SPRING-REST", "Spring para APIs REST", new BigDecimal("44.90"))
    );

    public Product findByCode(String productCode) {
        Product product = products.get(productCode);
        if (product == null) {
            throw new UnknownProductException(productCode);
        }
        return product;
    }

    public boolean contains(String productCode) {
        return products.containsKey(productCode);
    }

    public record Product(String code, String name, BigDecimal unitPrice) {
    }

    public static class UnknownProductException extends RuntimeException {
        public UnknownProductException(String code) {
            super("Produto não encontrado no catálogo: " + code);
        }
    }
}

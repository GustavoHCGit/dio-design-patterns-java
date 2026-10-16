package com.portfolio.patterns.domain;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

@Embeddable
public class OrderLine {
    private String productCode;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;

    protected OrderLine() {
    }

    public OrderLine(String productCode, String productName, int quantity, BigDecimal unitPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public String getProductCode() { return productCode; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getLineTotal() { return lineTotal; }
}

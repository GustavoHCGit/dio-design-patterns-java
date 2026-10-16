package com.portfolio.patterns.api;

import com.portfolio.patterns.domain.Order;
import com.portfolio.patterns.domain.OrderStatus;
import com.portfolio.patterns.domain.PaymentMethod;
import com.portfolio.patterns.domain.ShippingMethod;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        String customerName,
        List<OrderItemResponse> items,
        PaymentMethod paymentMethod,
        ShippingMethod shippingMethod,
        OrderStatus status,
        BigDecimal subtotal,
        BigDecimal shippingCost,
        BigDecimal total,
        String paymentReference,
        Instant createdAt) {

    public static OrderResponse from(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(item -> new OrderItemResponse(item.getProductCode(), item.getProductName(),
                        item.getQuantity(), item.getUnitPrice(), item.getLineTotal()))
                .toList();
        return new OrderResponse(order.getId(), order.getCustomerName(), items,
                order.getPaymentMethod(), order.getShippingMethod(), order.getStatus(),
                order.getSubtotal(), order.getShippingCost(), order.getTotal(),
                order.getPaymentReference(), order.getCreatedAt());
    }

    public record OrderItemResponse(String productCode, String productName, int quantity,
                                    BigDecimal unitPrice, BigDecimal lineTotal) {
    }
}

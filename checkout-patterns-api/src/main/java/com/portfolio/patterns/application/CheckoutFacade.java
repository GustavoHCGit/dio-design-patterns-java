package com.portfolio.patterns.application;

import com.portfolio.patterns.api.CreateOrderItemRequest;
import com.portfolio.patterns.api.CreateOrderRequest;
import com.portfolio.patterns.domain.Order;
import com.portfolio.patterns.domain.OrderLine;
import com.portfolio.patterns.repository.OrderRepository;
import com.portfolio.patterns.service.ProductCatalog;
import com.portfolio.patterns.service.payment.PaymentStrategyRegistry;
import com.portfolio.patterns.service.shipping.ShippingStrategyRegistry;
import com.portfolio.patterns.validation.CheckoutValidationChain;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckoutFacade {
    private final CheckoutValidationChain validationChain;
    private final ProductCatalog catalog;
    private final PaymentStrategyRegistry paymentStrategies;
    private final ShippingStrategyRegistry shippingStrategies;
    private final OrderRepository orderRepository;

    public CheckoutFacade(CheckoutValidationChain validationChain, ProductCatalog catalog,
                          PaymentStrategyRegistry paymentStrategies,
                          ShippingStrategyRegistry shippingStrategies,
                          OrderRepository orderRepository) {
        this.validationChain = validationChain;
        this.catalog = catalog;
        this.paymentStrategies = paymentStrategies;
        this.shippingStrategies = shippingStrategies;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order checkout(CreateOrderRequest request) {
        validationChain.validate(request);

        List<OrderLine> lines = request.items().stream()
                .map(this::toOrderLine)
                .toList();
        BigDecimal subtotal = lines.stream()
                .map(OrderLine::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal shippingCost = shippingStrategies.forMethod(request.shippingMethod()).calculate(subtotal);
        BigDecimal total = subtotal.add(shippingCost);
        String paymentReference = paymentStrategies.forMethod(request.paymentMethod()).authorize(total);

        Order order = new Order(request.customerName().trim(), lines, request.paymentMethod(),
                request.shippingMethod(), subtotal, shippingCost, total, paymentReference);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findById(long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    private OrderLine toOrderLine(CreateOrderItemRequest request) {
        ProductCatalog.Product product = catalog.findByCode(request.productCode());
        return new OrderLine(product.code(), product.name(), request.quantity(), product.unitPrice());
    }

    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(long id) {
            super("Pedido não encontrado: " + id);
        }
    }
}

package com.portfolio.patterns.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "line_position")
    private List<OrderLine> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShippingMethod shippingMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal shippingCost;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private String paymentReference;

    @Column(nullable = false)
    private Instant createdAt;

    protected Order() {
    }

    public Order(String customerName, List<OrderLine> items, PaymentMethod paymentMethod,
                 ShippingMethod shippingMethod, BigDecimal subtotal, BigDecimal shippingCost,
                 BigDecimal total, String paymentReference) {
        this.customerName = customerName;
        this.items = new ArrayList<>(items);
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.status = OrderStatus.CONFIRMED;
        this.subtotal = subtotal;
        this.shippingCost = shippingCost;
        this.total = total;
        this.paymentReference = paymentReference;
        this.createdAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public List<OrderLine> getItems() { return List.copyOf(items); }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public ShippingMethod getShippingMethod() { return shippingMethod; }
    public OrderStatus getStatus() { return status; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getShippingCost() { return shippingCost; }
    public BigDecimal getTotal() { return total; }
    public String getPaymentReference() { return paymentReference; }
    public Instant getCreatedAt() { return createdAt; }
}

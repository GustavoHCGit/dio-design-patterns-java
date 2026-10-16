package com.portfolio.patterns.repository;

import com.portfolio.patterns.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

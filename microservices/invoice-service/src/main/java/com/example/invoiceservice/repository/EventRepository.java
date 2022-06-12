package com.example.invoiceservice.repository;

import com.example.invoiceservice.domain.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<OrderEvent,Long  > {
    Optional<OrderEvent> findByOrderId(String orderId);
}

package com.statistics.app.repository;

import com.statistics.app.domain.AllOrders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface AllOrdersRepository extends JpaRepository<AllOrders, Long> {

    List<AllOrders> findAllByOrderDate(LocalDate orderDate);
}

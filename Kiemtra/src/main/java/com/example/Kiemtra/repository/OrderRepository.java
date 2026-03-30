package com.example.Kiemtra.repository;

import com.example.Kiemtra.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerName(String customerName);
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}

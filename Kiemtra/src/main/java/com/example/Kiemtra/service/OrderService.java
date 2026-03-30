package com.example.Kiemtra.service;

import com.example.Kiemtra.model.Order;
import com.example.Kiemtra.model.OrderDetail;
import com.example.Kiemtra.repository.OrderRepository;
import com.example.Kiemtra.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Order saveOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());
        
        // First, set the order reference on all details
        if (order.getDetails() != null) {
            for (OrderDetail detail : order.getDetails()) {
                detail.setOrder(order);
            }
        }
        
        // Save the order
        Order savedOrder = orderRepository.save(order);
        
        // Then explicitly save all order details
        if (order.getDetails() != null) {
            for (OrderDetail detail : order.getDetails()) {
                orderDetailRepository.save(detail);
            }
        }
        
        return savedOrder;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getOrdersByCustomerName(String customerName) {
        return orderRepository.findByCustomerName(customerName);
    }

    public List<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }
}

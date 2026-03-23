package com.example.Kiemtra.service;

import com.example.Kiemtra.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Order saveOrder(Order order) {
        order.setId(idGenerator.getAndIncrement());
        orders.add(order);
        return order;
    }
    
    public List<Order> getAllOrders() {
        return orders;
    }
}

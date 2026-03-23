package com.example.Kiemtra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private LocalDateTime orderDate;
    private String customerName;
    private String address;
    private String phone;
    private double totalAmount;
    private List<OrderDetail> details;
}

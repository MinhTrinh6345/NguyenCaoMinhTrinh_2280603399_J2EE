package com.example.Kiemtra.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`order`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
    
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    @Column(columnDefinition = "VARCHAR(500)")
    private String address;
    
    @Column(length = 20)
    private String phone;
    
    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
    
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDetail> details;
}

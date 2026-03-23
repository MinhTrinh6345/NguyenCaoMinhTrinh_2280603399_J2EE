package com.example.Kiemtra.service;

import com.example.Kiemtra.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1L, "Laptop Dell XPS 15", 1500.0, "High performance laptop", "Laptop"));
        products.add(new Product(2L, "MacBook Pro 14", 2000.0, "Apple M2 Pro chip", "Laptop"));
        products.add(new Product(3L, "iPhone 14 Pro Max", 1200.0, "Latest Apple smartphone", "Phone"));
        products.add(new Product(4L, "Samsung Galaxy S23 Ultra", 1150.0, "Android flagship phone", "Phone"));
        products.add(new Product(5L, "Sony WH-1000XM5", 400.0, "Noise cancelling headphones", "Audio"));
        products.add(new Product(6L, "iPad Air 5", 600.0, "M1 chip tablet", "Tablet"));
        products.add(new Product(7L, "Apple Watch Series 8", 399.0, "Smartwatch", "Accessory"));
        products.add(new Product(8L, "Samsung Galaxy Tab S8", 700.0, "Premium Android tablet", "Tablet"));
        products.add(new Product(9L, "Asus ROG Zephyrus G14", 1600.0, "Gaming laptop", "Laptop"));
        products.add(new Product(10L, "Google Pixel 7 Pro", 899.0, "Excellent camera phone", "Phone"));
        products.add(new Product(11L, "Lenovo ThinkPad X1 Carbon", 1800.0, "Business laptop", "Laptop"));
        products.add(new Product(12L, "Nintendo Switch OLED", 350.0, "Hybrid gaming console", "Gaming"));
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public List<String> getAllCategories() {
        return products.stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String keyword, String category, String sort) {
        List<Product> results = new ArrayList<>(products);

        // Filter by keyword
        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            results = results.stream()
                    .filter(p -> p.getName().toLowerCase().contains(lowerKeyword))
                    .collect(Collectors.toList());
        }

        // Filter by category
        if (category != null && !category.trim().isEmpty()) {
            results = results.stream()
                    .filter(p -> p.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        // Sort by price
        if (sort != null && !sort.trim().isEmpty()) {
            if (sort.equalsIgnoreCase("asc")) {
                results.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
            } else if (sort.equalsIgnoreCase("desc")) {
                results.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice()));
            }
        }

        return results;
    }
}

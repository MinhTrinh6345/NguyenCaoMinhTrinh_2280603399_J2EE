package com.example.Kiemtra.config;

import com.example.Kiemtra.model.Product;
import com.example.Kiemtra.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeData(ProductRepository productRepository) {
        return args -> {
            // Check if products already exist
            if (productRepository.count() == 0) {
                // Insert sample products
                productRepository.save(new Product(null, "Laptop Dell XPS 13", 1299.99, "High-performance laptop with Intel i7 processor", "Electronics"));
                productRepository.save(new Product(null, "iPhone 15", 999.99, "Latest Apple smartphone with A17 Pro chip", "Electronics"));
                productRepository.save(new Product(null, "Sony WH-1000XM5 Headphones", 399.99, "Premium noise-cancelling wireless headphones", "Electronics"));
                productRepository.save(new Product(null, "Samsung 4K Monitor", 599.99, "32-inch 4K UHD display for professional work", "Electronics"));
                productRepository.save(new Product(null, "Mechanical Keyboard RGB", 149.99, "Gaming mechanical keyboard with RGB lighting", "Accessories"));
                productRepository.save(new Product(null, "Wireless Mouse", 49.99, "Ergonomic wireless mouse for productivity", "Accessories"));
                productRepository.save(new Product(null, "USB-C Hub", 79.99, "7-in-1 USB-C hub with multiple ports", "Accessories"));
                productRepository.save(new Product(null, "Phone Stand", 29.99, "Adjustable phone stand for desk or travel", "Accessories"));
                productRepository.save(new Product(null, "Webcam 4K", 89.99, "Professional 4K webcam for streaming and video calls", "Electronics"));
                productRepository.save(new Product(null, "Memory Card 256GB", 59.99, "High-speed SD card for photography and video", "Storage"));
                
                System.out.println("✅ Sample products loaded into database successfully!");
            } else {
                System.out.println("✅ Products already exist in database. Skipping initialization.");
            }
        };
    }
}

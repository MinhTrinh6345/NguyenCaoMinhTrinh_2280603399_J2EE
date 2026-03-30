package com.example.Kiemtra.service;

import com.example.Kiemtra.model.Product;
import com.example.Kiemtra.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<String> getAllCategories() {
        return productRepository.findAll().stream()
                .map(Product::getCategory)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Product> searchProducts(String keyword, String category, String sort) {
        List<Product> results = new ArrayList<>(productRepository.findAll());

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

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}

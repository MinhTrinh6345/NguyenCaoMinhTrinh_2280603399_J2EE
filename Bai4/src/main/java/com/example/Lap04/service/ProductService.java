package com.example.Lap04.service;

import com.example.Lap04.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public List<Product> getAll() {
        return products;
    }

    public Product get(int id) {
        return products.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Product product) {
        int maxId = products.stream().mapToInt(Product::getId).max().orElse(0);
        product.setId(maxId + 1);
        products.add(product);
    }

    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());
            find.setCategory(editProduct.getCategory());
            if (editProduct.getImage() != null) {
                find.setImage(editProduct.getImage());
            }
        }
    }

    public void delete(int id) {
        products.removeIf(p -> p.getId() == id);
    }

    public void updateImage(Product product, MultipartFile file) {
        if (file == null || file.isEmpty()) return;

        try {
            Path dir = Paths.get("src/main/resources/static/images");
            if (!Files.exists(dir)) Files.createDirectories(dir);

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(),
                    dir.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            product.setImage(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

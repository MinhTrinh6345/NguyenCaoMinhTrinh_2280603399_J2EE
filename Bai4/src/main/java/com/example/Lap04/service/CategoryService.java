package com.example.Lap04.service;

import com.example.Lap04.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final List<Category> categories = List.of(
            new Category(1, "Điện thoại"),
            new Category(2, "Laptop")
    );

    public List<Category> getAll() {
        return categories;
    }

    public Category get(int id) {
        return categories.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}

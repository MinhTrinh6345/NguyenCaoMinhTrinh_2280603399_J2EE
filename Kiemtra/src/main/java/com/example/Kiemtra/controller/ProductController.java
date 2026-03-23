package com.example.Kiemtra.controller;

import com.example.Kiemtra.model.Product;
import com.example.Kiemtra.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {
        
        List<Product> results = productService.searchProducts(keyword, category, sort);
        
        int pageSize = 5;
        int totalItems = results.size();
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;
        
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalItems);
        
        List<Product> pageResults = startIndex < totalItems ? results.subList(startIndex, endIndex) : List.of();
        
        model.addAttribute("products", pageResults);
        model.addAttribute("categories", productService.getAllCategories());
        
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("sort", sort);
        
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        
        return "search";
    }
}

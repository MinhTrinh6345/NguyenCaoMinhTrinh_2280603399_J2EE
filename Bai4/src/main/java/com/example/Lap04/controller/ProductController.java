package com.example.Lap04.controller;

import com.example.Lap04.model.Product;
import com.example.Lap04.service.CategoryService;
import com.example.Lap04.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid Product product,
            BindingResult result,
            @RequestParam("category.id") int categoryId,
            @RequestParam("imageProduct") MultipartFile image,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        productService.updateImage(product, image);
        product.setCategory(categoryService.get(categoryId));
        productService.add(product);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product find = productService.get(id);
        if (find == null) return "error/404";

        model.addAttribute("product", find);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid Product product,
            BindingResult result,
            @RequestParam("imageProduct") MultipartFile image,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        productService.updateImage(product, image);
        productService.update(product);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}

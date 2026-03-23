package com.example.Kiemtra.controller;

import com.example.Kiemtra.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    private final OrderService orderService;

    public AdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/login")
    public String loginForm() {
        return "admin-login";
    }

    @PostMapping("/admin/login")
    public String processLogin(@RequestParam("username") String username, 
                               @RequestParam("password") String password, 
                               HttpSession session, 
                               Model model) {
        // Hardcoded generic credentials for the mockup
        if ("admin".equals(username) && "123456".equals(password)) {
            session.setAttribute("adminUser", username);
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu.");
        return "admin-login";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("adminUser") == null) {
            return "redirect:/admin/login";
        }
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin-dashboard";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("adminUser");
        return "redirect:/admin/login";
    }
}

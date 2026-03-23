package com.example.Kiemtra.controller;

import com.example.Kiemtra.model.CartItem;
import com.example.Kiemtra.model.Order;
import com.example.Kiemtra.model.OrderDetail;
import com.example.Kiemtra.model.Product;
import com.example.Kiemtra.service.OrderService;
import com.example.Kiemtra.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CartController {

    private final ProductService productService;
    private final OrderService orderService;

    public CartController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId, HttpSession session, RedirectAttributes redirectAttributes) {
        @SuppressWarnings("unchecked")
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        Product product = productService.getAllProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst().orElse(null);

        if (product != null) {
            if (cart.containsKey(productId)) {
                CartItem item = cart.get(productId);
                item.setQuantity(item.getQuantity() + 1);
            } else {
                cart.put(productId, new CartItem(product, 1));
            }
            session.setAttribute("cart", cart);
            redirectAttributes.addFlashAttribute("successMessage", "✅ Đã thêm " + product.getName() + " vào giỏ hàng thành công!");
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart != null && cart.containsKey(productId)) {
            if (quantity > 0) {
                cart.get(productId).setQuantity(quantity);
            } else {
                cart.remove(productId);
            }
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Long productId, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart != null && cart.containsKey(productId)) {
            cart.remove(productId);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        double total = cart.values().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();

        model.addAttribute("cartItems", cart.values());
        model.addAttribute("total", total);

        return "cart";
    }

    @GetMapping("/cart/checkout")
    public String checkoutForm(HttpSession session, Model model) {
        @SuppressWarnings("unchecked")
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        double total = cart.values().stream().mapToDouble(CartItem::getSubtotal).sum();
        model.addAttribute("cartItems", cart.values());
        model.addAttribute("total", total);

        return "checkout";
    }

    @PostMapping("/cart/checkout")
    public String processCheckout(
            @RequestParam("customerName") String customerName,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            HttpSession session) {
            
        @SuppressWarnings("unchecked")
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart != null && !cart.isEmpty()) {
            Order order = new Order();
            order.setOrderDate(LocalDateTime.now());
            order.setCustomerName(customerName);
            order.setPhone(phone);
            order.setAddress(address);
            
            double total = 0;
            List<OrderDetail> details = cart.values().stream().map(item -> {
                return new OrderDetail(item.getProduct(), item.getQuantity(), item.getProduct().getPrice());
            }).collect(Collectors.toList());
            
            for (OrderDetail detail : details) {
                total += detail.getPrice() * detail.getQuantity();
            }
            
            order.setDetails(details);
            order.setTotalAmount(total);
            
            orderService.saveOrder(order);
            
            session.removeAttribute("cart");
        }
        
        return "checkout-success";
    }
}

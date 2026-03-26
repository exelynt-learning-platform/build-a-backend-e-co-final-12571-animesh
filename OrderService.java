package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.OrderRepo;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;

    public Order createOrder(Long userId) {

        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = new Order();
        order.setUser(userRepo.findById(userId).orElseThrow());

        List<Product> products = cart.getItems().stream()
                .map(CartItem::getProduct)
                .toList();

        order.setProducts(products);

        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        order.setTotalPrice(total);
        order.setStatus("CREATED");

        // ✅ Reduce stock
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
        }

        cart.getItems().clear();

        return orderRepo.save(order);
    }
}
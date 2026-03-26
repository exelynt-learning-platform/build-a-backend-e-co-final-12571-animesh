package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    public OrderService(OrderRepo orderRepo, CartRepo cartRepo,
                        UserRepo userRepo, ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    public OrderEntity createOrder(Long userId) {

        Cart cart = cartRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        OrderEntity order = new OrderEntity();
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

        // ✅ FIX stock update
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepo.save(product);
        }

        cart.getItems().clear();
        cartRepo.save(cart);

        return orderRepo.save(order);
    }
}
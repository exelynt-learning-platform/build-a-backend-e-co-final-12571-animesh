package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.ProductRepo;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public Cart getUserCart(Long userId) {
        return cartRepo.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepo.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    Cart cart = new Cart();
                    cart.setUser(user);
                    cart.setItems(new ArrayList<>());
                    return cartRepo.save(cart);
                });
    }

    public Cart addToCart(Long userId, Long productId, int qty) {

        Cart cart = getUserCart(userId);

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ✅ STOCK VALIDATION
        if (qty > product.getStock()) {
            throw new RuntimeException("Not enough stock");
        }

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(qty);
        item.setCart(cart);

        cart.getItems().add(item);

        return cartRepo.save(cart);
    }

    public Cart removeItem(Long userId, Long itemId) {
        Cart cart = getUserCart(userId);
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return cartRepo.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getUserCart(userId);
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
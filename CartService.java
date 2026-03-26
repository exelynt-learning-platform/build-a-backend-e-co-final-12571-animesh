package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
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
        return cartRepo.findAll().stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .findFirst()
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(userRepo.findById(userId).orElseThrow());
                    cart.setItems(new ArrayList<>());
                    return cartRepo.save(cart);
                });
    }

    public Cart addToCart(Long userId, Long productId, int qty) {
        Cart cart = getUserCart(userId);
        Product product = productRepo.findById(productId).orElseThrow();

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(qty);

        cart.getItems().add(item);
        return cartRepo.save(cart);
    }

    public Cart removeItem(Long userId, Long itemId) {
        Cart cart = getUserCart(userId);
        cart.getItems().removeIf(i -> i.getId().equals(itemId));
        return cartRepo.save(cart);
    }
}
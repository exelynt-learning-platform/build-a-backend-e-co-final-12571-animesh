package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
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

    // ✅ Get or Create Cart for User
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

    // ✅ Add Product to Cart
    public Cart addToCart(Long userId, Long productId, int qty) {

        Cart cart = getUserCart(userId);

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if product already exists in cart
        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + qty);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(qty);
            item.setCart(cart);

            cart.getItems().add(item);
        }

        return cartRepo.save(cart);
    }

    // ✅ Remove Item from Cart
    public Cart removeItem(Long userId, Long itemId) {

        Cart cart = getUserCart(userId);

        cart.getItems().removeIf(item -> item.getId().equals(itemId));

        return cartRepo.save(cart);
    }

    // ✅ Update Quantity
    public Cart updateQuantity(Long userId, Long itemId, int qty) {

        Cart cart = getUserCart(userId);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found"));

        item.setQuantity(qty);

        return cartRepo.save(cart);
    }

    // ✅ Clear Cart (used after order)
    public void clearCart(Long userId) {
        Cart cart = getUserCart(userId);
        cart.getItems().clear();
        cartRepo.save(cart);
    }
}
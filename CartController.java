package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // ✅ Get user cart
    @GetMapping
    public Cart getCart(@RequestParam Long userId) {
        return cartService.getUserCart(userId);
    }

    // ✅ Add item to cart
    @PostMapping("/add")
    public Cart addToCart(@RequestParam Long userId,
                          @RequestParam Long productId,
                          @RequestParam int quantity) {

        return cartService.addToCart(userId, productId, quantity);
    }

    // ✅ Remove item from cart
    @DeleteMapping("/remove")
    public Cart removeItem(@RequestParam Long userId,
                           @RequestParam Long itemId) {

        return cartService.removeItem(userId, itemId);
    }

    // ✅ Clear cart
    @DeleteMapping("/clear")
    public String clearCart(@RequestParam Long userId) {

        cartService.clearCart(userId);
        return "Cart cleared successfully";
    }
}
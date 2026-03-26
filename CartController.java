package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor

public class CartController {

    private final CartService service;

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return service.getUserCart(userId);
    }

    @PostMapping("/add")
    public Cart add(@RequestParam Long userId,
                    @RequestParam Long productId,
                    @RequestParam int qty) {
        return service.addToCart(userId, productId, qty);
    }

    @DeleteMapping("/remove")
    public Cart remove(@RequestParam Long userId,
                       @RequestParam Long itemId) {
        return service.removeItem(userId, itemId);
    }
}

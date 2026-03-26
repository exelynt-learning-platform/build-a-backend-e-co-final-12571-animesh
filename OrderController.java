package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor

public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    public Order create(@RequestParam Long userId) {
        return service.createOrder(userId);
    }

    @GetMapping("/{userId}")
    public List<Order> getOrders(@PathVariable Long userId) {
        return service.getUserOrders(userId);
    }
}

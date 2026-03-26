package com.example.demo.controller;

import com.example.demo.entity.OrderEntity;
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
    public OrderEntity create(@RequestParam Long userId) {
        return service.createOrder(userId);
    }

    @GetMapping("/{userId}")
    public List<OrderEntity> getOrders(@PathVariable Long userId) {
        return service.getUserOrders(userId);
    }
}

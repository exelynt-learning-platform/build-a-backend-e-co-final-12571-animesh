package com.example.demo.controller;

import com.example.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor

public class PaymentController {

    private final PaymentService service;

    @PostMapping("/create")
    public String create(@RequestParam Double amount) throws Exception {
        return service.createPayment(amount);
    }
}
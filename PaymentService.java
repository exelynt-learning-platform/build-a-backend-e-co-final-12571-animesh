package com.example.demo.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    public String createPayment(Double amount) throws Exception {

        Stripe.apiKey = "sk_test_51TFFDMPFVe1V7u0nxGl2sjWlSttnfRjwsYkFvWFeQjvU3xfVJmyxo9TgQH0aGuAaJa0ShkxiYjcQLYIoBkY6Bkuw00H0EytH1s";

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(amount * 100));
        params.put("currency", "usd");

        PaymentIntent intent = PaymentIntent.create(params);
        return intent.getClientSecret();
    }
}

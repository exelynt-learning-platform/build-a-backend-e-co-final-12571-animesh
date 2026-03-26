package com.example.demo.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${stripe.api.key}")
    private String apiKey;

    private static final int CENTS = 100;

    public String createPayment(Double amount) throws Exception {

        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(amount * CENTS));
        params.put("currency", "usd");

        PaymentIntent intent = PaymentIntent.create(params);
        return intent.getClientSecret();
    }
}
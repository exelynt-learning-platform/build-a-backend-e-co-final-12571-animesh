package com.example.demo.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    public String createPayment(Double amount) throws Exception {

       

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(amount * 100));
        params.put("currency", "usd");

        PaymentIntent intent = PaymentIntent.create(params);
        return intent.getClientSecret();
    }
}

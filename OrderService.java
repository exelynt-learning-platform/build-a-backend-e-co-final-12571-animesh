package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.Order;
import com.example.demo.entity.Product;
import com.example.demo.repository.CartRepo;
import com.example.demo.repository.OrderRepo;
import com.example.demo.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;

    public Order createOrder(Long userId) {

        Cart cart = cartRepo.findAll().stream()
                .filter(c -> c.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = new Order();
        order.setUser(userRepo.findById(userId).orElseThrow());

        List<Product> products = cart.getItems().stream()
                .map(CartItem::getProduct)
                .toList();

        order.setProducts(products);

        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        order.setTotalPrice(total);
        order.setStatus("CREATED");

        cart.getItems().clear(); // clear cart after order

        return orderRepo.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepo.findAll().stream()
                .filter(o -> o.getUser().getId().equals(userId))
                .toList();
    }
}
package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    // ✅ CREATE PRODUCT
    public Product createProduct(Product product) {

        validateProduct(product);

        return productRepo.save(product);
    }

    // ✅ GET ALL PRODUCTS
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    // ✅ GET PRODUCT BY ID
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    // ✅ UPDATE PRODUCT
    public Product updateProduct(Long id, Product updated) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        validateProduct(updated);

        product.setName(updated.getName());
        product.setDescription(updated.getDescription());
        product.setPrice(updated.getPrice());
        product.setStock(updated.getStock());
        product.setImageUrl(updated.getImageUrl());

        return productRepo.save(product);
    }

    // ✅ DELETE PRODUCT
    public void deleteProduct(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        productRepo.delete(product);
    }

    // 🔒 VALIDATION METHOD
    private void validateProduct(Product product) {

        if (product.getName() == null || product.getName().isBlank()) {
            throw new BadRequestException("Product name is required");
        }

        if (product.getPrice() < 0) {
            throw new BadRequestException("Price cannot be negative");
        }

        if (product.getStock() < 0) {
            throw new BadRequestException("Stock cannot be negative");
        }
    }
}
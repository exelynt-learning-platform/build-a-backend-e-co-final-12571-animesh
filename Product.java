package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private double price;
    private int stock;
    private String imageUrl;
}
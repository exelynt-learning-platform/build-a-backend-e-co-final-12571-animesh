package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String role;

    // ✅ FIXED
    public String getRole() {
        return this.role;
    }
}
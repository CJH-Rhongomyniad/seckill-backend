package com.example.backend.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;

    private String name;

    private String title;

    private String img;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    // Getter and Setter methods
}
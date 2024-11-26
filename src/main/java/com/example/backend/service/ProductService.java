package com.example.backend.service;

import com.example.backend.entity.Category;
import com.example.backend.entity.Product;
import org.springframework.data.domain.Page;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Page<Product> findProducts(int page, int size, String keyword, Long categoryId, BigDecimal priceMin, BigDecimal priceMax);
    List<Category> findCategories();
    Product findById(Long id);
}

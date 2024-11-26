package com.example.backend.service;

import com.example.backend.entity.Category;
import com.example.backend.entity.Product;
import com.example.backend.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Page<Product> findProducts(int page, int size, String keyword, Long categoryId, BigDecimal priceMin, BigDecimal priceMax) {
        int offset = (page - 1) * size;
        List<Product> products = productMapper.findProducts(offset, size, keyword, categoryId, priceMin, priceMax);
        Pageable pageable = PageRequest.of(page - 1, size);
        return new PageImpl<>(products, pageable, products.size());
    }

    @Override
    public List<Category> findCategories() {
        return productMapper.findCategories();
    }

    @Override
    public Product findById(Long id) {
        return productMapper.findById(id);
    }
}
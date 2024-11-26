package com.example.backend.controller;

import com.example.backend.config.CommonResult;
import com.example.backend.entity.Category;
import com.example.backend.entity.Product;
import com.example.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public CommonResult<?> getProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax
    ) {
        Page<Product> productPage = productService.findProducts(page, size, keyword, category, priceMin, priceMax);
        return CommonResult.success(productPage);
    }

    @GetMapping("/categories")
    public CommonResult<?> getCategories() {
        List<Category> categories = productService.findCategories();
        return CommonResult.success(categories);
    }

    @GetMapping("/getProduct/{id}")
    public CommonResult<?> getProductDetail(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            return CommonResult.success(product);
        } else {
            return CommonResult.error((Integer) 400, "Product not found");
        }
    }
}
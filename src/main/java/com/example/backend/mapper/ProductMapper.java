package com.example.backend.mapper;

import com.example.backend.entity.Category;
import com.example.backend.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findProducts(@Param("offset") int offset, @Param("size") int size, @Param("keyword") String keyword,
                               @Param("categoryId") Long categoryId, @Param("priceMin") BigDecimal priceMin, @Param("priceMax") BigDecimal priceMax);
    List<Category> findCategories();
    Product findById(Long id);
}

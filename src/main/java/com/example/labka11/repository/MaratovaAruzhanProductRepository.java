package com.example.labka11.repository;

import com.example.labka11.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaratovaAruzhanProductRepository extends JpaRepository<Product, Long> {
    // поиск по имени (фильтрация)
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // поиск по цене (фильтрация)
    Page<Product> findByPriceLessThanEqual(double price, Pageable pageable);
}

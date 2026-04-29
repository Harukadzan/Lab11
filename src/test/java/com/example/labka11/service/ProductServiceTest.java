package com.example.labka11.service;

import com.example.labka11.entity.Product;
import com.example.labka11.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Используем Mockito фейковый обьект
class ProductServiceTest {

    @Mock
    private ProductRepository repo;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(1000.0);

        when(repo.save(any(Product.class))).thenReturn(product);

        Product saved = productService.create(product);

        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
        verify(repo, times(1)).save(any(Product.class)); // Проверяем вызов репозитория
    }
}

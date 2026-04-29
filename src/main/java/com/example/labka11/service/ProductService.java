package com.example.labka11.service;

import com.example.labka11.dto.ProductDTO;
import com.example.labka11.entity.Product;
import com.example.labka11.exception.NotFoundException;
import com.example.labka11.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repo;

    public ProductService(ProductRepository repo){
        this.repo = repo;
    }

    public Product create(Product p){
        log.info("Creating product: {}", p.getName());
        return repo.save(p);
    }

    public List<Product> getAll(){
        log.info("Fetching all products");
        return repo.findAll();
    }

    public Product getById(Long id){
        log.info("Fetching product by id: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Товар не найден с id: " + id));
    }

    public Product update(Long id, ProductDTO dto){
        log.info("Updating product id: {}", id);
        Product p = getById(id);
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        return repo.save(p);
    }

    public void delete(Long id){
        log.info("Deleting product id: {}", id);
        if(!repo.existsById(id)){
            throw new NotFoundException("Товар не найден с id: " + id);
        }
        repo.deleteById(id);
    }
}
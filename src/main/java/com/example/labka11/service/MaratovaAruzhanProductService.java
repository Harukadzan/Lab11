package com.example.labka11.service;

import com.example.labka11.dto.MaratovaAruzhanProductDTO;
import com.example.labka11.entity.Product;
import com.example.labka11.exception.MaratovaAruzhanNotFoundException;
import com.example.labka11.repository.MaratovaAruzhanProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaratovaAruzhanProductService {

    private static final Logger log = LoggerFactory.getLogger(MaratovaAruzhanProductService.class);
    private final MaratovaAruzhanProductRepository repo;

    public MaratovaAruzhanProductService(MaratovaAruzhanProductRepository repo){
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
                .orElseThrow(() -> new MaratovaAruzhanNotFoundException("Товар не найден с id: " + id));
    }

    public Product update(Long id, MaratovaAruzhanProductDTO dto){
        log.info("Updating product id: {}", id);
        Product p = getById(id);
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        return repo.save(p);
    }

    public void delete(Long id){
        log.info("Deleting product id: {}", id);
        if(!repo.existsById(id)){
            throw new MaratovaAruzhanNotFoundException("Товар не найден с id: " + id);
        }
        repo.deleteById(id);
    }

    // добавь этот метод в сервис
    public Page<Product> getAll(String name, Double maxPrice, Pageable pageable) {
        log.info("Fetching products with filters: name={}, maxPrice={}", name, maxPrice);

        if (name != null) {
            return repo.findByNameContainingIgnoreCase(name, pageable);
        }
        if (maxPrice != null) {
            return repo.findByPriceLessThanEqual(maxPrice, pageable);
        }
        return repo.findAll(pageable);
    }
}
package com.example.labka11.controller;

import com.example.labka11.dto.MaratovaAruzhanProductDTO;
import com.example.labka11.entity.Product;
import com.example.labka11.service.MaratovaAruzhanProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class MaratovaAruzhanProductController {

    private final MaratovaAruzhanProductService service;

    public MaratovaAruzhanProductController(MaratovaAruzhanProductService service){
        this.service = service;
    }

    // замени старый getAll на этот:
    @GetMapping
    public Page<Product> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return service.getAll(name, maxPrice, pageable);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody @Valid MaratovaAruzhanProductDTO dto){
        Product p = new Product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        return service.create(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody @Valid MaratovaAruzhanProductDTO dto){
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok("Товар удалён");
    }
}
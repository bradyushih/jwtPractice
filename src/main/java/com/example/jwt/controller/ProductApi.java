package com.example.jwt.controller;

import com.example.jwt.entity.Product;
import com.example.jwt.jpa.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductApi {

    @Autowired
    private ProductRepository prodRepo;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody @Valid Product product){
        Product savedProduct = prodRepo.save(product);
        URI productURI = URI.create("/products/" + savedProduct.getId());
        return ResponseEntity.created(productURI).body(savedProduct);
    }

    @GetMapping
    public List<Product> list() {
        return prodRepo.findAll();
    }
}

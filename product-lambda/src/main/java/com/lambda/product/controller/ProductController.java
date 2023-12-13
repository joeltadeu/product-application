package com.lambda.product.controller;

import com.lambda.product.model.Product;
import com.lambda.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @PostMapping("/products")
  ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
    var createdProduct = service.save(product);
    return ResponseEntity.ok(createdProduct);
  }

  @GetMapping("/products")
  ResponseEntity<List<Product>> findAll() {
    var productList = service.findAll();
    return ResponseEntity.ok(productList);
  }

  @GetMapping("/products/{id}")
  ResponseEntity<Product> getProduct(@PathVariable String id) {
    var product = service.findById(id);
    return ResponseEntity.ok(product);
  }
}

package com.lambda.product.service;

import com.lambda.product.model.Product;
import com.lambda.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class ProductService {

  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public List<Product> findAll() {
    return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
  }

  public Product findById(String id) {
    return repository.findById(id).orElseThrow(RuntimeException::new);
  }

  public Product save(Product product) {
    repository.save(product);
    return product;
  }
}

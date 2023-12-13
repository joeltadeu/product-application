package com.kinesis.product.consumer;

import java.util.function.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinesis.product.client.ProductClient;
import com.kinesis.product.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ProductConsumer {

  private final ProductClient productClient;

  public ProductConsumer(ProductClient productClient) {
    this.productClient = productClient;
  }

  @Bean
  Consumer<String> processProduct() {
    return (value) -> {
      try {
        var product = new ObjectMapper().readValue(value, Product.class);
        productClient.save(product);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    };
  }
}

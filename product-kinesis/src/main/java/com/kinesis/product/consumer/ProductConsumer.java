package com.kinesis.product.consumer;

import java.util.function.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinesis.product.client.ProductClient;
import com.kinesis.product.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
public class ProductConsumer {

  private final ProductClient productClient;

  public ProductConsumer(ProductClient productClient) {
    this.productClient = productClient;
  }

  @Bean
  Consumer<String> processProduct() {
    return (value) -> {
      try {
        log.info("Receiving message from kinesis: {}", value);
        var product = new ObjectMapper().readValue(value, Product.class);
        var productSaved = productClient.save(product);
        log.info("Product saved id: {}", productSaved.getId());
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    };
  }
}

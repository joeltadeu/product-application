package com.product.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductConsumerKinesisApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductConsumerKinesisApplication.class, args);
  }
}

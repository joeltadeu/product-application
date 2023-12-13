package com.kinesis.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductKinesisApplication {

  public static void main(String[] args) {

    SpringApplication.run(ProductKinesisApplication.class, args);
  }

}

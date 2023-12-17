package com.lambda.product;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDynamoDBRepositories(basePackages = "com.lambda.product")
public class ProductLambdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductLambdaApplication.class, args);
	}

}

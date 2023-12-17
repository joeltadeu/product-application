package com.lambda.product.repository;

import com.lambda.product.model.ProductImage;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ProductImageRepository extends CrudRepository<ProductImage, String> {}

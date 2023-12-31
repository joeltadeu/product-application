package com.kinesis.product.client;

import com.kinesis.product.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="ProductApi", url = "${product-url}")
public interface ProductClient {
    @RequestMapping(method = RequestMethod.POST, value = "/products", consumes = "application/json")
    Product save(Product product);
}

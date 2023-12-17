package com.lambda.product.controller;

import com.lambda.product.model.ProductImage;
import com.lambda.product.service.ProductImageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class ProductImageController {

    private final ProductImageService service;

    public ProductImageController(ProductImageService service) {
        this.service = service;
    }

    @PostMapping("/products/{productId}/images")
    ResponseEntity<Void> uploadImage(
            @PathVariable String productId,
            @NotNull
            @Pattern(regexp = "^[image/png|image/jpeg|image/jpg]*$")
            @RequestHeader(value = "Content-Type", required = true) String contentType,
            @Valid @RequestBody Resource body) {
        var productImage = service.save(productId, contentType, body);
        var imageUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{imageId}")
                .buildAndExpand(productImage.getId()).toUri();

        return ResponseEntity.created(imageUri).build();
    }

    @GetMapping("/products/{productId}/images/{imageId}")
    ResponseEntity<Resource> downloadImage(@PathVariable String productId, @PathVariable String imageId) {
        var fileInfo = service.getImage(imageId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(fileInfo.getContentType()))
                .body(fileInfo.getResource());
    }
}

package com.lambda.product.service;

import com.lambda.product.model.FileInfo;
import com.lambda.product.model.ProductImage;
import com.lambda.product.repository.ProductImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class ProductImageService {

    private final S3Service s3Service;

    private final ProductImageRepository repository;

    public ProductImageService(S3Service s3Service, ProductImageRepository repository) {
        this.s3Service = s3Service;
        this.repository = repository;
    }

    public ProductImage save(String id, String contentType, Resource body) {
        var mimeType = MimeTypeUtils.parseMimeType(contentType);
        String filename = UUID.randomUUID() + "." + mimeType.getSubtype();

        try {
            var fileInfo = s3Service.uploadObjectToS3(filename, contentType, body.getContentAsByteArray());
            if (fileInfo.isUploaded()) {
                var productImage = ProductImage.builder()
                        .productId(id)
                        .contentType(contentType)
                        .createdAt(LocalDateTime.now().toString())
                        .build();
                return repository.save(productImage);
            } else {
                throw new RuntimeException("Error to upload file");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error to upload file");
        }
    }

    public FileInfo getImage(String imageId) {
        var productImage = repository.findById(imageId).orElseThrow(RuntimeException::new);
        try (var stream = s3Service.downloadFileFromS3Bucket(productImage.getName())) {
            return FileInfo.builder()
                    .fileName(productImage.getName())
                    .contentType(productImage.getContentType())
                    .resource(new ByteArrayResource(stream.readAllBytes(), productImage.getContentType()))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Error to download file");
        }
    }


}

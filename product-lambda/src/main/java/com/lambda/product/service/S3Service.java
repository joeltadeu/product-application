package com.lambda.product.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.lambda.product.model.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@Service
@Slf4j
public class S3Service {

    @Value("${config.aws.s3.url}")
    private String s3EndpointUrl;

    @Value("${config.aws.s3.bucket-name}")
    private String s3BucketName;

    private final AmazonS3 amazonS3;

    public S3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public S3ObjectInputStream downloadFileFromS3Bucket(final String fileName) {
        log.info("Downloading file '{}' from bucket: '{}'", fileName, s3BucketName);
        final S3Object s3Object = amazonS3.getObject(s3BucketName, fileName);
        return s3Object.getObjectContent();
    }

    public FileInfo uploadObjectToS3(String filename, String contentType, byte[] content) {
        log.info("Uploading file '{}' to bucket: '{}'", filename, s3BucketName);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(content.length);
        String fileUrl = s3EndpointUrl + "/" + s3BucketName + "/" + filename;
        PutObjectResult putObjectResult = amazonS3.putObject(s3BucketName, filename, byteArrayInputStream, objectMetadata);
        return FileInfo.builder()
                .fileName(filename)
                .fileUrl(fileUrl)
                .contentType(contentType)
                .uploaded(Objects.nonNull(putObjectResult))
                .build();
    }
}

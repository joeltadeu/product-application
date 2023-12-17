package com.lambda.product.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;

@Data
@Builder
public class FileInfo {
    private String id;
    private String fileName;
    private String fileUrl;
    private String contentType;
    private Boolean uploaded;
    private Resource resource;

    public Boolean isUploaded() {
        return uploaded;
    }
}

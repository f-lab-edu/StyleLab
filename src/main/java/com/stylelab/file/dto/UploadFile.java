package com.stylelab.file.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UploadFile(
        MultipartFile multipartFile,
        String uploadFilename
) {

    public static UploadFile createUploadFile(MultipartFile multipartFile, String uploadFilename) {
        return UploadFile.builder()
                .multipartFile(multipartFile)
                .uploadFilename(uploadFilename)
                .build();
    }
}

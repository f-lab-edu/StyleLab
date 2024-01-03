package com.stylelab.file.presentation.response;

import com.stylelab.file.dto.UploadResult;
import lombok.Builder;

@Builder
public record ImageUploadResponse(UploadResult result) {

    public static ImageUploadResponse createResponse(UploadResult uploadResult) {
        return ImageUploadResponse.builder()
                .result(uploadResult)
                .build();
    }
}

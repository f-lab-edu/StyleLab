package com.stylelab.store.presentation.response;

import com.stylelab.file.dto.UploadResult;

public record ImageUploadResponse(UploadResult result) {

    public static ImageUploadResponse createResponse(UploadResult uploadResult) {
        return new ImageUploadResponse(uploadResult);
    }
}

package com.stylelab.file.dto;

import java.util.List;

public record UploadResult(
        List<SuccessImageUrl> imageUrls,
        List<FailureFilename> failureFilenames
) {

    public record SuccessImageUrl(String imageUrl) {
        public static SuccessImageUrl createSuccessImageUrl(String imageUrl) {
            return new SuccessImageUrl(imageUrl);
        }
    }

    public record FailureFilename(String originFilename) {
        public static FailureFilename createFailureFilename(String originFilename) {
            return new FailureFilename(originFilename);
        }
    }

    public static UploadResult createUploadResult(List<SuccessImageUrl> successImageUrls, List<FailureFilename> failureFilenames) {
        return new UploadResult(successImageUrls, failureFilenames);
    }
}

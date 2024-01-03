package com.stylelab.file.dto;

import lombok.Builder;

import java.util.List;

@Builder
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
        return UploadResult.builder()
                .imageUrls(successImageUrls)
                .failureFilenames(failureFilenames)
                .build();
    }
}

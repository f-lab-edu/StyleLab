package com.stylelab.file.application;

import com.stylelab.file.constant.ExtensionType;
import com.stylelab.file.dto.UploadFile;
import com.stylelab.file.dto.UploadResult;
import com.stylelab.file.exception.FileError;
import com.stylelab.file.exception.FileException;
import com.stylelab.file.presentation.response.ImageUploadResponse;
import com.stylelab.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileFacade {

    private final FileService fileService;

    public ImageUploadResponse uploadMultipartFiles(final List<MultipartFile> multipartFiles) {
        validationMultipartFiles(multipartFiles);

        List<UploadFile> uploadFiles = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            String uploadFilename = generatorFileName(extension);
            uploadFiles.add(UploadFile.createUploadFile(multipartFile, uploadFilename));
        }

        UploadResult uploadResult = fileService.uploadMultipartFiles(uploadFiles);
        return ImageUploadResponse.createResponse(uploadResult);
    }

    private void validationMultipartFiles(final List<MultipartFile> multipartFiles) {
        if (ObjectUtils.isEmpty(multipartFiles)) {
            throw new FileException(FileError.FILE_OBJECT_REQUIRE, FileError.FILE_OBJECT_REQUIRE.getMessage());
        }

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                throw new FileException(FileError.FILE_SIZE_CANNOT_LESS_THEN_ZERO, FileError.FILE_SIZE_CANNOT_LESS_THEN_ZERO.getMessage());
            }
            if (!StringUtils.hasText(multipartFile.getOriginalFilename())) {
                throw new FileException(FileError.FILE_ORIGIN_NAME_REQUIRE, FileError.FILE_ORIGIN_NAME_REQUIRE.getMessage());
            }

            String originalFilename = multipartFile.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
            if (!StringUtils.hasText(extension) || !validationExtension(extension)) {
                log.error("originFilename:{} , extension: {}", originalFilename, extension);
                throw new FileException(FileError.INVALID_FORMAT_FILE, FileError.INVALID_FORMAT_FILE.getMessage());
            }
        }
    }

    private boolean validationExtension(final String extension) {
        return Arrays.stream(ExtensionType.values())
                .anyMatch(extensionType -> extensionType.getExtension().equalsIgnoreCase(extension));
    }

    private String generatorFileName(final String extension) {
        return String.format("%s_%s%s", UUID.randomUUID(), System.nanoTime(), extension);
    }
}

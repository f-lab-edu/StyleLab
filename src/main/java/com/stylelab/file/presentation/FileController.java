package com.stylelab.file.presentation;

import com.stylelab.common.dto.ApiResponse;
import com.stylelab.file.application.FileFacade;
import com.stylelab.file.presentation.response.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileFacade fileFacade;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ImageUploadResponse>> uploadMultipartFiles(
            @RequestPart(name = "files", required = false) List<MultipartFile> multipartFiles) {
        return new ResponseEntity<>(ApiResponse.createApiResponse(fileFacade.uploadMultipartFiles(multipartFiles)), HttpStatus.CREATED);
    }
}

package com.stylelab.file.application;

import com.stylelab.file.dto.UploadResult;
import com.stylelab.file.exception.FileError;
import com.stylelab.file.exception.FileException;
import com.stylelab.file.presentation.response.ImageUploadResponse;
import com.stylelab.file.service.FileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FileFacadeTest {

    @Mock
    private FileService fileService;

    @InjectMocks
    private FileFacade fileFacade;

    @Nested
    @DisplayName("이미지 업로드 테스트")
    public class UploadMultipartFilesTest {

        @Test
        @DisplayName("이미지 업로드 실패 - MultipartFiles가 empty면 FileException(FILE_OBJECT_REQUIRE)이 발생한다.")
        void failureUploadMultipartFiles_01() {
            // given
            List<MultipartFile> multipartFiles = null;

            // when
            FileException fileException = assertThrows(FileException.class,
                    () -> fileFacade.uploadMultipartFiles(multipartFiles));

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.FILE_OBJECT_REQUIRE.getCode(), fileException.getServiceError().getCode());
        }

        @Test
        @DisplayName("이미지 업로드 실패 - MultipartFile의 size가 0이면 FileException(FILE_SIZE_CANNOT_LESS_THEN_ZERO)이 발생한다.")
        void failureUploadMultipartFiles_02() {
            // given
            List<MultipartFile> multipartFiles = Collections.singletonList(
                    new MockMultipartFile("file", "mock_file.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[0])
            );

            // when
            FileException fileException = assertThrows(FileException.class,
                    () -> fileFacade.uploadMultipartFiles(multipartFiles));

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.FILE_SIZE_CANNOT_LESS_THEN_ZERO.getCode(), fileException.getServiceError().getCode());
        }

        @Test
        @DisplayName("이미지 업로드 실패 - MultipartFile의 originFilename이 empty면 FileException(FILE_ORIGIN_NAME_REQUIRE)이 발생한다.")
        void failureUploadMultipartFiles_03() {
            // given
            List<MultipartFile> multipartFiles = Collections.singletonList(
                    new MockMultipartFile("file", "", MediaType.APPLICATION_PDF_VALUE, new byte[]{'C', 'O', 'D', 'E'})
            );

            // when
            FileException fileException = assertThrows(FileException.class,
                    () -> fileFacade.uploadMultipartFiles(multipartFiles));

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.FILE_ORIGIN_NAME_REQUIRE.getCode(), fileException.getServiceError().getCode());
        }

        @Test
        @DisplayName("이미지 업로드 실패 - MultipartFile의 확장자가 png, jpg, jpeg, webp가 아니면 FileException(INVALID_FORMAT_FILE)이 발생한다.")
        void failureUploadMultipartFiles_04() {
            // given
            List<MultipartFile> multipartFiles = Collections.singletonList(
                    new MockMultipartFile("file", "mock_file.pdf", MediaType.APPLICATION_PDF_VALUE, new byte[]{'C', 'O', 'D', 'E'})
            );

            // when
            FileException fileException = assertThrows(FileException.class,
                    () -> fileFacade.uploadMultipartFiles(multipartFiles));

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.INVALID_FORMAT_FILE.getCode(), fileException.getServiceError().getCode());
        }

        @Test
        @DisplayName("이미지 업로드 성공 - 두 개의 업로드 파일 중 한 개는 성공하고 한 개는 실패하여 응답 객체를 생성한다.")
        void successUploadMultipartFiles() {
            // given
            List<MultipartFile> multipartFiles = Arrays.asList(
                    new MockMultipartFile("file", "mock_file.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_fail_file.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'})
            );
            UploadResult uploadResult = UploadResult.createUploadResult(
                    Collections.singletonList(UploadResult.SuccessImageUrl.createSuccessImageUrl("bucket/products/mock_file.jpeg")),
                    Collections.singletonList(UploadResult.FailureFilename.createFailureFilename("bucket/products/mock_fail_file.jpeg"))
            );
            given(fileService.uploadMultipartFiles(anyList()))
                    .willReturn(uploadResult);

            // when
            ImageUploadResponse imageUploadResponse = fileFacade.uploadMultipartFiles(multipartFiles);

            // then
            verify(fileService, times(1))
                    .uploadMultipartFiles(anyList());
            assertEquals(uploadResult.imageUrls().get(0).imageUrl(), imageUploadResponse.result().imageUrls().get(0).imageUrl());
            assertEquals(uploadResult.failureFilenames().get(0).originFilename(), imageUploadResponse.result().failureFilenames().get(0).originFilename());
        }
    }
}

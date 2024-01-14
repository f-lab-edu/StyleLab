package com.stylelab.file.application;

import com.stylelab.file.constant.ImageType;
import com.stylelab.file.dto.UploadResult;
import com.stylelab.file.exception.FileError;
import com.stylelab.file.exception.FileException;
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
                    () -> fileFacade.uploadMultipartFiles(ImageType.PRODUCT_ENTRY_MAIN, multipartFiles));

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
                    () -> fileFacade.uploadMultipartFiles(ImageType.PRODUCT_ENTRY_MAIN, multipartFiles));

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
                    () -> fileFacade.uploadMultipartFiles(ImageType.PRODUCT_ENTRY_MAIN, multipartFiles));

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
                    () -> fileFacade.uploadMultipartFiles(ImageType.PRODUCT_ENTRY_MAIN, multipartFiles));

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.INVALID_FORMAT_FILE.getCode(), fileException.getServiceError().getCode());
        }

        @Test
        @DisplayName("이미지 업로드 실패 - ImageType이 null인 FileException(IMAGE_TYPE_REQUIRE)이 발생한다.")
        void failureUploadMultipartFiles_05() {
            // given
            List<MultipartFile> multipartFiles = Collections.singletonList(
                    new MockMultipartFile("file", "mock_file.pdf", MediaType.APPLICATION_PDF_VALUE, new byte[]{'C', 'O', 'D', 'E'})
            );

            // when
            FileException fileException = assertThrows(FileException.class,
                    () -> fileFacade.uploadMultipartFiles(null, multipartFiles));

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.IMAGE_TYPE_REQUIRE.getCode(), fileException.getServiceError().getCode());
        }

        @Test
        @DisplayName("이미지 업로드 실패 - ImageType이 PRODUCT_ENTRY_MAIN인 경우 이미지 개수가 2개 이상이면 FileException(EXCEED_MAX_IMAGE_COUNT)이 발생한다.")
        void failureUploadMultipartFiles_06() {
            // given
            List<MultipartFile> multipartFiles = Arrays.asList(
                    new MockMultipartFile("file", "mock_file1.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file2.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'})
            );

            // when
            FileException fileException = assertThrows(FileException.class,
                    () -> fileFacade.uploadMultipartFiles(ImageType.PRODUCT_ENTRY_MAIN, multipartFiles));
            System.out.println(fileException.getMessage());

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.EXCEED_MAX_IMAGE_COUNT.getCode(), fileException.getServiceError().getCode());
        }

        @Test
        @DisplayName("이미지 업로드 실패 - ImageType이 PRODUCT_ENTRY_SUB 경우 이미지 개수가 6개 이상이면 FileException(EXCEED_MAX_IMAGE_COUNT)이 발생한다.")
        void failureUploadMultipartFiles_07() {
            // given
            List<MultipartFile> multipartFiles = Arrays.asList(
                    new MockMultipartFile("file", "mock_file1.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file2.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file3.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file4.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file5.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file6.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'})
            );

            // when
            FileException fileException = assertThrows(FileException.class,
                    () -> fileFacade.uploadMultipartFiles(ImageType.PRODUCT_ENTRY_SUB, multipartFiles));
            System.out.println(fileException.getMessage());

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.EXCEED_MAX_IMAGE_COUNT.getCode(), fileException.getServiceError().getCode());
        }

        @Test
        @DisplayName("이미지 업로드 실패 - ImageType이 PRODUCT_ENTRY_SUB 경우 이미지 개수가 11개 이상이면 FileException(EXCEED_MAX_IMAGE_COUNT)이 발생한다.")
        void failureUploadMultipartFiles_08() {
            // given
            List<MultipartFile> multipartFiles = Arrays.asList(
                    new MockMultipartFile("file", "mock_file1.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file2.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file3.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file4.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file5.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file6.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file7.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file8.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file9.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file10.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file11.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'}),
                    new MockMultipartFile("file", "mock_file12.jpeg", MediaType.IMAGE_JPEG_VALUE, new byte[]{'C', 'O', 'D', 'E'})
            );

            // when
            FileException fileException = assertThrows(FileException.class,
                    () -> fileFacade.uploadMultipartFiles(ImageType.PRODUCT_DESCRIPTION, multipartFiles));
            System.out.println(fileException.getMessage());

            // then
            verify(fileService, times(0))
                    .uploadMultipartFiles(anyList());
            assertEquals(FileError.EXCEED_MAX_IMAGE_COUNT.getCode(), fileException.getServiceError().getCode());
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
            UploadResult result = fileFacade.uploadMultipartFiles(ImageType.PRODUCT_ENTRY_SUB, multipartFiles);

            // then
            verify(fileService, times(1))
                    .uploadMultipartFiles(anyList());
            assertEquals(uploadResult.imageUrls().get(0).imageUrl(), result.imageUrls().get(0).imageUrl());
            assertEquals(uploadResult.failureFilenames().get(0).originFilename(), result.failureFilenames().get(0).originFilename());
        }
    }
}

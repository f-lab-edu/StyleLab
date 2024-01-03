package com.stylelab.file.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.stylelab.file.constant.ExtensionType;
import com.stylelab.file.dto.UploadFile;
import com.stylelab.file.dto.UploadResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AwsS3ServiceTest {

    @Mock
    private AmazonS3 amazonS3Client;

    @InjectMocks
    private AwsS3Service awsS3Service;

    @BeforeEach
    public void init() {
        awsS3Service = new AwsS3Service("bucket", amazonS3Client);
    }

    @Nested
    @DisplayName("aws 이미지 업로드 테스트")
    public class UploadMultipartFilesTest {

        @Test
        @DisplayName("aws 이미지 업로드 성공 - 두 개의 UploadFile을 받아서 전부 업로드에 성공한 UploadResult를 생성한다.")
        void successUploadMultipartFilesTest() throws Exception {
            // given
            String originFilename1 = "mock_file1.jpeg";
            String originFilename2 = "mock_file2.jpeg";
            String uploadFilename1 = generatorFileName(ExtensionType.JPEG.getExtension());
            String uploadFilename2 = generatorFileName(ExtensionType.JPEG.getExtension());
            List<UploadFile> uploadFiles = Arrays.asList(
                    UploadFile.createUploadFile(
                            new MockMultipartFile("files", originFilename1, MediaType.IMAGE_JPEG_VALUE, new byte[]{'J', 'A', 'V', 'A'}),
                            uploadFilename1
                    ),
                    UploadFile.createUploadFile(
                            new MockMultipartFile("files", originFilename2, MediaType.IMAGE_JPEG_VALUE, new byte[]{'J', 'A', 'V', 'A'}),
                            uploadFilename2
                    )
            );
            URL uploadUrl1 = new URL("https://bucket.s3.ap-northeast-2.amazonaws.com/products/" + uploadFilename1);
            URL uploadUrl2 = new URL("https://bucket.s3.ap-northeast-2.amazonaws.com/products/" + uploadFilename2);
            given(amazonS3Client.getUrl(anyString(), anyString()))
                    .willReturn(uploadUrl1)
                    .willReturn(uploadUrl2);

            // when
            UploadResult uploadResult =awsS3Service.uploadMultipartFiles(uploadFiles);

            // then
            assertEquals(uploadUrl1.toExternalForm(), uploadResult.imageUrls().get(0).imageUrl());
            assertEquals(uploadUrl2.toExternalForm(), uploadResult.imageUrls().get(1).imageUrl());
            verify(amazonS3Client, times(2))
                    .putObject(any());
            verify(amazonS3Client, times(2))
                    .getUrl(anyString(), anyString());
        }

        @Test
        @DisplayName("aws 이미지 업로드 성공 - 두 개의 UploadFile을 받아서 성공한 한 개의 imageUrl과 실패한 originFilename을 가지는 UploadResult를 생성한다.")
        void partialSuccessUploadMultipartFilesTest() throws Exception {
            // given
            String originFilename1 = "mock_file1.jpeg";
            String originFilename2 = "mock_file2.jpeg";
            String uploadFilename1 = generatorFileName(ExtensionType.JPEG.getExtension());
            String uploadFilename2 = generatorFileName(ExtensionType.JPEG.getExtension());
            List<UploadFile> uploadFiles = Arrays.asList(
                    UploadFile.createUploadFile(
                            new MockMultipartFile("files", originFilename1, MediaType.IMAGE_JPEG_VALUE, new byte[]{'J', 'A', 'V', 'A'}),
                            uploadFilename1
                    ),
                    UploadFile.createUploadFile(
                            new MockMultipartFile("files", originFilename2, MediaType.IMAGE_JPEG_VALUE, new byte[]{'J', 'A', 'V', 'A'}),
                            uploadFilename2
                    )
            );
            URL uploadUrl1 = new URL("https://bucket.s3.ap-northeast-2.amazonaws.com/products/" + uploadFilename1);
            given(amazonS3Client.putObject(any()))
                    .willReturn(new PutObjectResult())
                    .willThrow(new AmazonClientException("putObject fail"));
            given(amazonS3Client.getUrl(anyString(), anyString()))
                    .willReturn(uploadUrl1);

            // when
            UploadResult uploadResult =awsS3Service.uploadMultipartFiles(uploadFiles);

            // then
            assertEquals(uploadUrl1.toExternalForm(), uploadResult.imageUrls().get(0).imageUrl());
            assertEquals(originFilename2, uploadResult.failureFilenames().get(0).originFilename());
            verify(amazonS3Client, times(2))
                    .putObject(any());
            verify(amazonS3Client, times(1))
                    .getUrl(anyString(), anyString());
        }
    }

    private String generatorFileName(final String extension) {
        return String.format("%s_%s%s", UUID.randomUUID(), System.nanoTime(), extension);
    }
}

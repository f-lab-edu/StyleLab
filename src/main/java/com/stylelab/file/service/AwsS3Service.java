package com.stylelab.file.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.stylelab.file.dto.UploadFile;
import com.stylelab.file.dto.UploadResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.stylelab.file.dto.UploadResult.FailureFilename;
import static com.stylelab.file.dto.UploadResult.SuccessImageUrl;

@Slf4j
@Service
public class AwsS3Service implements FileService {


    private final String bucket;
    private final AmazonS3 amazonS3Client;
    private final static String PREFIX_KEY_PATH = "products/";

    public AwsS3Service(@Value("${cloud.aws.s3.bucket}") String bucket, AmazonS3 amazonS3Client) {
        this.bucket = bucket;
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    public UploadResult uploadMultipartFiles(final List<UploadFile> uploadFiles) {

        List<SuccessImageUrl> imageUrls = new ArrayList<>();
        List<FailureFilename> failureFilename = new ArrayList<>();
        for (UploadFile uploadFile : uploadFiles) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(uploadFile.multipartFile().getSize());
            objectMetadata.setContentType(uploadFile.multipartFile().getContentType());
            String keyPath = PREFIX_KEY_PATH + uploadFile.uploadFilename();

            try (InputStream inputStream = uploadFile.multipartFile().getInputStream()) {
                amazonS3Client.putObject(
                        new PutObjectRequest(bucket, keyPath, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
                imageUrls.add(SuccessImageUrl.createSuccessImageUrl(amazonS3Client.getUrl(bucket, keyPath).toExternalForm()));
            } catch (Exception e) {
                log.error("com.stylelab.file.service.AwsS3Service.uploadMultipartFiles s3 upload fail: {}", uploadFile.multipartFile().getOriginalFilename(), e);
                failureFilename.add(FailureFilename.createFailureFilename(uploadFile.multipartFile().getOriginalFilename()));
            }
        }

        return UploadResult.createUploadResult(imageUrls, failureFilename);
    }
}

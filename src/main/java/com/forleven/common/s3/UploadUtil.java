package com.forleven.common.s3;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import io.vavr.control.Either;
import io.vavr.control.Try;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Component
public class UploadUtil {

    private final AmazonS3 s3client;

    public UploadUtil(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public Either<UploadError, MultipartFile> imageStore(@NotNull UploadDsl uploadDsl) {

        log.info("Try store image");

        if (StringUtils.isEmpty(uploadDsl.getFilename())) {
            log.info("Image filename empty");
            return Either.left(UploadError.EMPTY_FILENAME);
        }

        if (StringUtils.isEmpty(uploadDsl.getFile().getContentType())) {
            log.info("Image content empty");
            return Either.left(UploadError.EMPTY_CONTENT_TYPE);
        }

        boolean isInvalidImage = !Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp")
                .contains(uploadDsl.getFile().getContentType());

        if (isInvalidImage) {
            log.info("Image mime type invalid");
            return Either.left(UploadError.INVALID_CONTENT_TYPE);
        }

        return store(uploadDsl);
    }

    public Either<UploadError, MultipartFile> store(@NotNull UploadDsl uploadDsl) {

        String bucketName = uploadDsl.getBucketName();
        String filename = uploadDsl.getFilename();
        MultipartFile file = uploadDsl.getFile();
        ObjectMetadata metadata = uploadDsl.getMetadata();

        if (StringUtils.isEmpty(bucketName)) {
            log.info("Bucket empty");
            return Either.left(UploadError.EMPTY_BUCKET_NAME);
        }

        if (StringUtils.isEmpty(filename)) {
            log.info("Filename empty");
            return Either.left(UploadError.EMPTY_FILENAME);
        }

        if (StringUtils.isEmpty(FilenameUtils.getExtension(file.getOriginalFilename()))) {
            log.info("Filename invalid");
            return Either.left(UploadError.INVALID_FILENAME);
        }

        return Try
                .of(() -> {

                    byte[] bytes = IOUtils.toByteArray(file.getInputStream());

                    metadata.setContentLength(bytes.length);
                    metadata.setContentType(file.getContentType());

                    return s3client.putObject(new PutObjectRequest(
                            bucketName,
                            filename,
                            new ByteArrayInputStream(bytes),
                            metadata
                    ).withCannedAcl(uploadDsl.getCannedAcl()));
                })
                .toEither()
                .mapLeft(error -> {
                    log.error(error.getMessage());
                    return UploadError.ERROR_IN_PROGRESS;
                })
                .map(result -> {
                    log.info("Success in store '{}/{}'", bucketName, filename);
                    return file;
                });
    }

}

package com.forleven.common.s3;

import java.util.Arrays;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import io.vavr.control.Either;
import io.vavr.control.Try;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

enum UploadError {
    EMPTY_BUCKET_NAME,
    EMPTY_CONTENT_TYPE,
    EMPTY_FILENAME,
    INVALID_CONTENT_TYPE,
    INVALID_FILENAME,
    ERROR_IN_PROGRESS
}

@Data
@Builder
class UploadDsl {

    @NotNull
    private String bucketName;

    private String filename;

    @NotNull
    private MultipartFile file;

    @NotNull
    private ObjectMetadata metadata;

    @Nullable
    public String getFilename() {

        if (!StringUtils.isEmpty(filename)) {
            return filename;
        }

        // builder produce file = null
        if (file == null) {
            return null;
        }

        return file.getOriginalFilename();
    }

    @Nullable
    public ObjectMetadata getMetadata() {

        if (!StringUtils.isEmpty(metadata)) {
            return metadata;
        }

        return new ObjectMetadata();
    }

}


@Slf4j
@Component
public class UploadUtil {

    @Autowired
    private AmazonS3 s3client;

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

        boolean isInvalidImage = !Arrays.asList("image/jpeg", "image/png", "image/gif").contains(uploadDsl.getFile().getContentType());

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

        return Try.of(() -> s3client.putObject(bucketName, filename, file.getInputStream(), metadata))
                .toEither()
                .mapLeft(error -> UploadError.ERROR_IN_PROGRESS)
                .map(result -> {
                    log.info("Success in store");
                    return file;
                });
    }

}
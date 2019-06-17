package com.forleven.common.s3;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import io.vavr.control.Either;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UploadUtilTests {

    @Autowired
    private UploadUtil uploadUtil;

    @Test
    public void testImageStoreWithSuccess() throws IOException {

        // GIVEN - a file

        MultipartFile file = new MockMultipartFile(
                "file",
                "file2.jpg",
                "image/png",
                new URL("https://site.forleven.com/img/logotipo_white_no_shadow.png").openStream()
        );


        // WHEN - try put file in s3 util

        UploadDsl dsl = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .build();

        Either<UploadError, MultipartFile> imageStoreResult = uploadUtil.imageStore(dsl);

        UploadDsl dsl1 = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .filename(UUID.randomUUID() + DateTimeFormat.forPattern("yyMMddhhmmss").print(new DateTime()))
                .build();

        Either<UploadError, MultipartFile> imageStoreResult1 = uploadUtil.imageStore(dsl1);

        // THEN - success in store

        assertTrue(imageStoreResult.isRight());

        assertEquals(
                file,
                imageStoreResult.get()
        );

        assertTrue(imageStoreResult1.isRight());

        assertEquals(
                file,
                imageStoreResult1.get()
        );
    }

    @Test
    public void testImageStoreWithFilenameEmpty() throws IOException {

        // GIVEN - a file with invalid mime type

        MultipartFile file = new MockMultipartFile(
                "file",
                "",
                "image/gif",
                new URL("https://site.forleven.com/img/logotipo_white_no_shadow.png").openStream()
        );


        // WHEN - try put file in s3 util

        UploadDsl dsl = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .build();

        Either<UploadError, MultipartFile> imageStoreResult = uploadUtil.imageStore(dsl);

        UploadDsl dsl1 = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .filename("")
                .build();

        Either<UploadError, MultipartFile> imageStoreResult1 = uploadUtil.imageStore(dsl1);

        // THEN - error in store

        assertTrue(imageStoreResult.isLeft());

        assertEquals(
                UploadError.EMPTY_FILENAME,
                imageStoreResult.getLeft()
        );

        assertTrue(imageStoreResult1.isLeft());

        assertEquals(
                UploadError.EMPTY_FILENAME,
                imageStoreResult1.getLeft()
        );
    }

    @Test
    public void testImageStoreWithContentTypeEmpty() throws IOException {

        // GIVEN - a file with invalid mime type

        MultipartFile file = new MockMultipartFile(
                "file",
                "file1.jpg",
                "",
                new URL("https://site.forleven.com/img/logotipo_white_no_shadow.png").openStream()
        );


        // WHEN - try put file in s3 util

        UploadDsl dsl = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .build();

        Either<UploadError, MultipartFile> imageStoreResult = uploadUtil.imageStore(dsl);

        // THEN - error in store

        assertTrue(imageStoreResult.isLeft());

        assertEquals(
                UploadError.EMPTY_CONTENT_TYPE,
                imageStoreResult.getLeft()
        );
    }

    @Test
    public void testImageStoreWithMimeTypeInvalid() throws IOException {

        // GIVEN - a file with invalid mime type

        MultipartFile file = new MockMultipartFile(
                "file",
                "file1.jpg",
                "test",
                new URL("https://site.forleven.com/img/logotipo_white_no_shadow.png").openStream()
        );


        // WHEN - try put file in s3 util

        UploadDsl dsl = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .build();

        Either<UploadError, MultipartFile> imageStoreResult = uploadUtil.imageStore(dsl);

        // THEN - error in store

        assertTrue(imageStoreResult.isLeft());

        assertEquals(
                UploadError.INVALID_CONTENT_TYPE,
                imageStoreResult.getLeft()
        );
    }

    @Test
    public void testStoreWithSuccess() throws IOException {

        // GIVEN - a file

        MultipartFile file = new MockMultipartFile(
                "file",
                "file2.jpg",
                "image/png",
                new URL("https://site.forleven.com/img/logotipo_white_no_shadow.png").openStream()
        );


        // WHEN - try put file in s3 util

        UploadDsl dsl = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .build();

        Either<UploadError, MultipartFile> storeResult = uploadUtil.store(dsl);


        UploadDsl dsl1 = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .build();

        Either<UploadError, MultipartFile> storeResult1 = uploadUtil.store(dsl1);

        UploadDsl dsl2 = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .metadata(new ObjectMetadata())
                .build();

        Either<UploadError, MultipartFile> storeResult2 = uploadUtil.store(dsl2);

        // THEN - success in store

        assertTrue(storeResult.isRight());

        assertEquals(
                file,
                storeResult.get()
        );

        assertTrue(storeResult1.isRight());

        assertEquals(
                file,
                storeResult1.get()
        );

        assertTrue(storeResult2.isRight());

        assertEquals(
                file,
                storeResult2.get()
        );
    }

    @Test
    public void testStoreWithFilenameEmpty() throws IOException {

        // GIVEN - a file

        MultipartFile file = new MockMultipartFile(
                "file",
                "",
                "image/png",
                new URL("https://site.forleven.com/img/logotipo_white_no_shadow.png").openStream()
        );


        // WHEN - try put file in s3 util


        UploadDsl dsl = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .build();

        Either<UploadError, MultipartFile> storeResult = uploadUtil.store(dsl);


        UploadDsl dsl1 = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .filename("")
                .build();

        Either<UploadError, MultipartFile> storeResult1 = uploadUtil.store(dsl1);

        // THEN - error in store

        assertTrue(storeResult.isLeft());

        assertEquals(
                UploadError.EMPTY_FILENAME,
                storeResult.getLeft()
        );

        assertTrue(storeResult1.isLeft());

        assertEquals(
                UploadError.EMPTY_FILENAME,
                storeResult1.getLeft()
        );
    }

    @Test
    public void testStoreWithFilenameInvalid() throws IOException {

        // GIVEN - a file

        MultipartFile file = new MockMultipartFile(
                "file",
                " ",
                "image/png",
                new URL("https://site.forleven.com/img/logotipo_white_no_shadow.png").openStream()
        );


        // WHEN - try put file in s3 util

        UploadDsl dsl = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .build();

        Either<UploadError, MultipartFile> storeResult = uploadUtil.store(dsl);

        UploadDsl dsl1 = UploadDsl.builder()
                .bucketName("common-bucket")
                .file(file)
                .filename("fl")
                .build();

        Either<UploadError, MultipartFile> storeResult1 = uploadUtil.store(dsl1);

        // THEN - error in store

        assertTrue(storeResult.isLeft());

        assertEquals(
                UploadError.INVALID_FILENAME,
                storeResult.getLeft()
        );

        assertTrue(storeResult1.isLeft());

        assertEquals(
                UploadError.INVALID_FILENAME,
                storeResult1.getLeft()
        );

    }

    @Test
    public void testStoreWithBucketInvalidName() throws IOException {

        // GIVEN - a file

        MultipartFile file = new MockMultipartFile(
                "file",
                "file.png",
                "image/png",
                new URL("https://site.forleven.com/img/logotipo_white_no_shadow.png").openStream()
        );


        // WHEN - try put file in s3 util


        UploadDsl dsl = UploadDsl.builder()
                .build();

        Either<UploadError, MultipartFile> storeResult = uploadUtil.store(dsl);


        UploadDsl dsl1 = UploadDsl.builder()
                .file(file)
                .build();

        Either<UploadError, MultipartFile> imageStoreResult1 = uploadUtil.imageStore(dsl1);

        // THEN - error in store

        assertTrue(storeResult.isLeft());

        assertEquals(
                UploadError.EMPTY_BUCKET_NAME,
                storeResult.getLeft()
        );

        assertTrue(imageStoreResult1.isLeft());

        assertEquals(
                UploadError.EMPTY_BUCKET_NAME,
                imageStoreResult1.getLeft()
        );
    }

}

@Configuration
@ActiveProfiles({"test"})
class DisableAWSConfiguration {

    @Bean(name = "amazonS3")
    @Primary
    public AmazonS3 amazonS3() {
        return mock(AmazonS3.class);
    }

}
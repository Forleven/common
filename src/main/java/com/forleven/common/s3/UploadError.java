package com.forleven.common.s3;

public enum UploadError {
    EMPTY_BUCKET_NAME,
    EMPTY_CONTENT_TYPE,
    EMPTY_FILENAME,
    INVALID_CONTENT_TYPE,
    INVALID_FILENAME,
    ERROR_IN_PROGRESS
}

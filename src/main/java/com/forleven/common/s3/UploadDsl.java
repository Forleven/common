package com.forleven.common.s3;

import lombok.Builder;
import lombok.Data;

import com.amazonaws.services.s3.model.ObjectMetadata;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class UploadDsl {

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

package com.forleven.common;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.forleven.common.exception.ExceptionHandlerController;
import com.forleven.common.i18n.MessageUtil;
import com.forleven.common.s3.UploadDsl;
import com.forleven.common.validation.FormErrors;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableJpaAuditing
@Import({
        MessageUtil.class,
        ExceptionHandlerController.class,
        FormErrors.class,
        UploadDsl.class
})
public @interface EnableForlevenCommon {
}

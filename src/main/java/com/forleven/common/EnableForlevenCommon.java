package com.forleven.common;

import java.lang.annotation.*;

import org.springframework.context.annotation.Import;

import com.forleven.common.exception.ExceptionHandlerController;
import com.forleven.common.i18n.MessageUtil;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({MessageUtil.class, ExceptionHandlerController.class})
public @interface EnableForlevenCommon {
}
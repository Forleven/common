package com.forleven.common.i18n;

import io.vavr.control.Try;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String code) {
        return Try.of(() -> messageSource.getMessage(code, null, LocaleContextHolder.getLocale()))
                .getOrElse(code);
    }

    public String getMessage(String code, Object[] args) {
        return Try.of(() -> messageSource.getMessage(code, args, LocaleContextHolder.getLocale()))
                .getOrElse(code);
    }

}

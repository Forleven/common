package com.forleven.common.validation;

import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.forleven.common.i18n.MessageUtil;
import com.forleven.common.web.ResourceErrors;
import com.forleven.common.web.ResponseError;

@Slf4j
@Component
public class FormErrors {

    @Autowired
    private MessageUtil messageUtil;

    public ResourceErrors beanValidation(BindingResult bindingResult) {

        List<ObjectError> allErrors = bindingResult.getAllErrors();

        List<ResponseError> errors = allErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(e -> new ResponseError(e, messageUtil.getMessage(e)))
                .collect(Collectors.toList());

        return new ResourceErrors(errors);
    }
}

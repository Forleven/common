package com.forleven.common.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.forleven.common.i18n.MessageUtil;
import com.forleven.common.web.ResourceErrors;
import com.forleven.common.web.ResponseError;

@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResourceErrors responseError(ConstraintViolationException ex) {
        return new ResourceErrors(ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .map(e -> new ResponseError(e, messageUtil.getMessage(e)))
                .collect(Collectors.toList()));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResourceErrors responseError(NotFoundException e) {
        return new ResourceErrors(Collections.singletonList(
                new ResponseError(e.getMessage(), messageUtil.getMessage(e.getMessage()))
        ));
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ResourceErrors responseError(ConflictException e) {
        return new ResourceErrors(Collections.singletonList(
                new ResponseError(e.getMessage(), messageUtil.getMessage(e.getMessage(), e.getArgs()))
        ));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResourceErrors responseError(BadRequestException e) {
        return new ResourceErrors(Collections.singletonList(
                new ResponseError(e.getMessage(), messageUtil.getMessage(e.getMessage(), e.getArgs()))
        ));
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ResourceErrors responseError(ForbiddenException e) {
        return new ResourceErrors(Collections.singletonList(
                new ResponseError(e.getMessage(), messageUtil.getMessage(e.getMessage(), e.getArgs()))
        ));
    }
}

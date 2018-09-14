package com.forleven.common.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.forleven.common.i18n.MessageUtil;
import com.forleven.common.web.ResponseError;

@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    private MessageUtil messageUtil;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseError responseError(NotFoundException e) {
        return new ResponseError(e.getMessage(), messageUtil.getMessage(e.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public ResponseError responseError(ConflictException e) {
        return new ResponseError(e.getMessage(), messageUtil.getMessage(e.getMessage(), e.getArgs()));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseError responseError(BadRequestException e) {
        return new ResponseError(e.getMessage(), messageUtil.getMessage(e.getMessage(), e.getArgs()));
    }
}

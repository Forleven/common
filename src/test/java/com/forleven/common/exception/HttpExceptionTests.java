package com.forleven.common.exception;

import com.forleven.common.i18n.MessageUtil;
import com.forleven.common.web.ResourceErrors;
import com.forleven.common.web.ResponseError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
public class HttpExceptionTests {

    @Mock
    MessageUtil messageUtil;

    @InjectMocks
    ExceptionHandlerController exceptionHandlerController;

    @Test
    public void test_different_exceptions() {
        assertNotEquals(
                new NotFoundException("message"),
                new NotFoundException("message1")
        );

        assertNotEquals(
                new BadRequestException("message"),
                new NotFoundException("message")
        );

        assertNotEquals(
                new BadRequestException("message"),
                new NotFoundException("message1")
        );
    }

    @Test
    public void test_equal_exceptions() {
        assertEquals(
                new NotFoundException("message"),
                new NotFoundException("message")
        );
    }

    @Test
    public void forbiddenException() {

        ForbiddenException forbiddenException = new ForbiddenException("message");

        ResourceErrors resourceErrorsMock = new ResourceErrors(Collections.singletonList(
                new ResponseError(
                        "message",
                        messageUtil.getMessage("message", forbiddenException.getArgs())
                )));

        ResourceErrors resourceErrors = exceptionHandlerController.responseError(forbiddenException);

        assertEquals(
                resourceErrorsMock.getClass(),
                resourceErrors.getClass()
        );
    }
}
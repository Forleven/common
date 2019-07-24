package com.forleven.common.exception;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpExceptionTests {

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
    }

}
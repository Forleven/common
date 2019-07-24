package com.forleven.common.exception;

import java.util.Arrays;

import lombok.Data;

@Data
public class HttpException extends RuntimeException {

    private final String[] args;

    public HttpException() {
        this.args = new String[]{};
    }

    public HttpException(String codeError) {
        super(codeError);
        this.args = new String[]{};
    }

    public HttpException(String codeError,
                  String... args) {
        super(codeError);
        this.args = args;
    }

    @Override
    public String toString() {
        return "HttpException(message=" + getMessage() + ", args=" + Arrays.deepToString(this.getArgs()) + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof HttpException)) return false;
        final HttpException other = (HttpException) o;
        if (!other.canEqual((Object) this)) return false;
        if (!getMessage().equals(other.getMessage())) return false;
        if (!Arrays.deepEquals(this.getArgs(), other.getArgs())) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HttpException;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        result = result * PRIME + Arrays.deepHashCode(this.getArgs());
        return result;
    }
}

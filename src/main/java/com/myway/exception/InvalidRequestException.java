package com.myway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ebru Göksal
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends RuntimeException {

    private String field;
    private String expected;

    public InvalidRequestException(String field, Object value, Object expected) {
        super("Invalid value for field " + field + " : { " + (value == null ? " [ null ] " : value.toString()) + " }. Should be { " + expected.toString() + " }" );
        this.field = field;
        this.expected = expected.toString();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }
}


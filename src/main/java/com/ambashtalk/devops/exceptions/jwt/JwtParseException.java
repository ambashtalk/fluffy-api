package com.ambashtalk.devops.exceptions.jwt;

public class JwtParseException extends RuntimeException {
    public JwtParseException() {
        super();
    }

    public JwtParseException(String message) {
        super(message);
    }
}

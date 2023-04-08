package com.ambashtalk.devops.exceptions.jwt;

public class TokenRefreshException extends RuntimeException {
    public TokenRefreshException() {
        super();
    }

    public TokenRefreshException(String message) {
        super(message);
    }
}

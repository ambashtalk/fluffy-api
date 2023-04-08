package com.ambashtalk.devops.exceptions.role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}

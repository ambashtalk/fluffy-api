package com.ambashtalk.devops.exceptions.person;

public class PersonAlreadyExistsException extends RuntimeException {
    public PersonAlreadyExistsException() {
        super();
    }

    public PersonAlreadyExistsException(String message) {
        super(message);
    }
}

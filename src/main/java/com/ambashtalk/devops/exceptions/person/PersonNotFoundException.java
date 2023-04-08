package com.ambashtalk.devops.exceptions.person;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super();
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}

package com.airtribe.learntrack.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        System.out.println(message);
    }
}

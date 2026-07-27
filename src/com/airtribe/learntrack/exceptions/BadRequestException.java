package com.airtribe.learntrack.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        System.out.println(message);
    }

}

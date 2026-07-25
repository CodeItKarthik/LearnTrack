package com.airtribe.learntrack.exceptions;

public class CancelInputException extends RuntimeException {

    public CancelInputException(String message) {
        System.out.println(message);
    }

}

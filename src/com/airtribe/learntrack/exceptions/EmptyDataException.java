package com.airtribe.learntrack.exceptions;

public class EmptyDataException extends RuntimeException {

    public EmptyDataException(String message) {
        System.out.println(message);
    }

}

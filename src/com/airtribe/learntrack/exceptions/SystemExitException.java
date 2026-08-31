package com.airtribe.learntrack.exceptions;

public class SystemExitException extends RuntimeException{

    public SystemExitException(String message) {
        System.out.println(message);
    }

}

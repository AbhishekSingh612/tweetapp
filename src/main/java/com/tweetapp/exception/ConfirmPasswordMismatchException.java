package com.tweetapp.exception;

public class ConfirmPasswordMismatchException extends RuntimeException {

    public ConfirmPasswordMismatchException(String message) {
        super(message);
    }
}

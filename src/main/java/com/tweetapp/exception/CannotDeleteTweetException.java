package com.tweetapp.exception;

public class CannotDeleteTweetException extends RuntimeException {
    public CannotDeleteTweetException(String message) {
        super(message);
    }
}

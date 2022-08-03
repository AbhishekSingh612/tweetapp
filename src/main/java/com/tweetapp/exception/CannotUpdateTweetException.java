package com.tweetapp.exception;

public class CannotUpdateTweetException extends RuntimeException {
    public CannotUpdateTweetException(String message) {
        super(message);
    }
}

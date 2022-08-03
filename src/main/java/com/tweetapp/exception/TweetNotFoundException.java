package com.tweetapp.exception;

public class TweetNotFoundException extends RuntimeException {

    public TweetNotFoundException(String message){
        super(message);
    }
}

package com.tweetapp.repository;

import com.tweetapp.entity.AppUser;
import com.tweetapp.entity.Tweet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface TweetRepository{
    List<Tweet> findByAuthor(String author);
    Optional<Tweet> findById(String id);
    List<Tweet> findAll();
    Tweet save(Tweet tweet);
    void delete(Tweet tweet);
}

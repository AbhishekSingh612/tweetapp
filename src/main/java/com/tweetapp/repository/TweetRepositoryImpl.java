package com.tweetapp.repository;

import com.tweetapp.entity.Tweet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TweetRepositoryImpl implements TweetRepository {
    @Override
    public List<Tweet> findByAuthor(String author) {
        return null;
    }

    @Override
    public Optional<Tweet> findById(String id) {
        return Optional.empty();
    }

    @Override
    public List<Tweet> findAll() {
        return null;
    }

    @Override
    public Tweet save(Tweet tweet) {
        return null;
    }

    @Override
    public void delete(Tweet tweet) {

    }
}

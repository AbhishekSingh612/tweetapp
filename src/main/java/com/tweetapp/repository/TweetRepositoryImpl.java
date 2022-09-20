package com.tweetapp.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.tweetapp.entity.Tweet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TweetRepositoryImpl implements TweetRepository {

    @Autowired
    DynamoDBMapper dbMapper;

    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

    @Override
    public List<Tweet> findByAuthor(String author) {
        List<Tweet> tweets = findAll();
        return tweets.stream().filter(tweet -> StringUtils.equals(tweet.getAuthor().getUserId(),author)).collect(Collectors.toList());
    }

    @Override
    public Optional<Tweet> findById(String id) {
        return Optional.ofNullable(dbMapper.load(Tweet.class, id));
    }

    @Override
    public List<Tweet> findAll() {
        PaginatedScanList<Tweet> tweets = dbMapper.scan(Tweet.class, scanExpression);
        if (tweets == null)
            return Collections.emptyList();
        return new ArrayList<>(tweets);
    }

    @Override
    public Tweet save(Tweet tweet) {
        dbMapper.save(tweet);
        return tweet;
    }

    @Override
    public void delete(Tweet tweet) {
        dbMapper.delete(tweet);
    }
}

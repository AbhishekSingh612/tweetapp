package com.tweetapp.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.tweetapp.entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyRepositoryImpl implements ReplyRepository {

    @Autowired
    DynamoDBMapper dbMapper;

    @Override
    public Reply save(Reply reply) {
        dbMapper.save(reply);
        return reply;
    }
}

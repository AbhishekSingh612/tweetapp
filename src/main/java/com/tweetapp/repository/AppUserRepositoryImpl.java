package com.tweetapp.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.tweetapp.entity.AppUser;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AppUserRepositoryImpl implements AppUserRepository {

    @Autowired
    DynamoDBMapper dbMapper;

    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

    @Override
    public Optional<AppUser> findById(String id) {
        return Optional.of(dbMapper.load(AppUser.class, id));
    }

    @Override
    public List<AppUser> findAll() {
        return dbMapper.scan(AppUser.class, scanExpression);
    }

    @Override
    public AppUser save(AppUser appUser) {
        dbMapper.save(appUser);
        return appUser;
    }

    @Override
    public Optional<AppUser> findByEmailOrUserId(String email, String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(userId));

        DynamoDBQueryExpression<AppUser> queryExpression = new DynamoDBQueryExpression<AppUser>()
                .withKeyConditionExpression("userId  = :val1").withExpressionAttributeValues(eav);

        List<AppUser> appUsers = dbMapper.query(AppUser.class, queryExpression);


        eav = new HashMap<>();
        eav.put(":val2", new AttributeValue().withS(email));
        if (CollectionUtils.isNotEmpty(appUsers))
            return Optional.of(appUsers.get(0));

        queryExpression = new DynamoDBQueryExpression<AppUser>()
                .withKeyConditionExpression("email = :val2").withExpressionAttributeValues(eav);

        appUsers = dbMapper.query(AppUser.class, queryExpression);

        return Optional.of(appUsers.get(0));
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return Optional.of(dbMapper.load(AppUser.class, email));
    }

    @Override
    public List<AppUser> findByEmailOrUserIdLikeRegex(String regex) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(regex));
        eav.put(":val2", new AttributeValue().withS(regex));

        DynamoDBQueryExpression<AppUser> queryExpression = new DynamoDBQueryExpression<AppUser>()
                .withFilterExpression("CONTAINS(userId,:v1) or CONTAINS(email,:v2)")
                .withExpressionAttributeValues(eav);

        return dbMapper.query(AppUser.class, queryExpression);
    }
}

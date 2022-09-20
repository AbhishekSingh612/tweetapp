package com.tweetapp.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.tweetapp.entity.AppUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AppUserRepositoryImpl implements AppUserRepository {

    @Autowired
    DynamoDBMapper dbMapper;

    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

    @Override
    public Optional<AppUser> findById(String id) {
        return Optional.ofNullable(dbMapper.load(AppUser.class, id));
    }

    @Override
    public List<AppUser> findAll() {
        PaginatedScanList<AppUser> appUsers = dbMapper.scan(AppUser.class, scanExpression);
        if (appUsers == null)
            return Collections.emptyList();
        return  new ArrayList<>(appUsers);
    }

    @Override
    public AppUser save(AppUser appUser) {
        dbMapper.save(appUser);
        return appUser;
    }

    @Override
    public Optional<AppUser> findByEmailOrUserId(String email, String userId) {

        PaginatedScanList<AppUser> appUsers = dbMapper.scan(AppUser.class, scanExpression);
        if (appUsers == null)
            return Optional.empty();
        List<AppUser> users = new ArrayList<>(appUsers);
        return users.stream().filter(user -> StringUtils.equals(user.getUserId(), userId) || StringUtils.equals(user.getEmail(), email)).findFirst();
    }

    @Override
    public Optional<AppUser> findByEmail(String email) {
        return findAll().stream().filter(appUser -> StringUtils.equals(appUser.getEmail(), email)).findFirst();
    }

    @Override
    public List<AppUser> findByEmailOrUserIdLikeRegex(String regex) {
        PaginatedScanList<AppUser> appUsers = dbMapper.scan(AppUser.class, scanExpression);
        if (appUsers == null)
            return Collections.emptyList();
        List<AppUser> users = new ArrayList<>(appUsers);
        return users.stream().filter(user -> StringUtils.contains(user.getUserId(), regex) || StringUtils.contains(user.getEmail(), regex)).collect(Collectors.toList());
    }
}

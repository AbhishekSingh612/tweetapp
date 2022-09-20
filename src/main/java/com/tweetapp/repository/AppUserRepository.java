package com.tweetapp.repository;

import com.tweetapp.entity.AppUser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface AppUserRepository{

    Optional<AppUser> findById(String id);
    List<AppUser> findAll();

    AppUser save(AppUser appUser);

    Optional<AppUser> findByEmailOrUserId(String email, String userId);

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findByEmailOrUserIdLikeRegex(String regex);
}

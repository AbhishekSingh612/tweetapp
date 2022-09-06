package com.tweetapp.repository;

import com.tweetapp.entity.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser,String> {

    @Query("{'$or':[ {'email': ?0 }, {'userId': ?1 } ] }")
    Optional<AppUser> findByEmailOrUserId(String email, String userId);

    Optional<AppUser> findByEmail(String email);

    @Query("{'$or':[ {'email':{$regex : ?0, $options: 'i'}}, {'userId':{$regex : ?0, $options: 'i'}} ] }")
    List<AppUser> findByEmailOrUserIdRegex(String regex);



}

package com.tweetapp.service;

import com.tweetapp.entity.AppUser;
import com.tweetapp.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.tweetapp.constants.AppConstants.*;

@Service
public class TweetUserDetailService implements UserDetailsService {
    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmailOrUserId(username, username).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
        return new User(username, appUser.getPassword(),Collections.emptyList());
    }
}

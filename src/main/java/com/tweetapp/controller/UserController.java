package com.tweetapp.controller;

import com.tweetapp.dto.AppUsers;
import com.tweetapp.dto.JwtTokenResponse;
import com.tweetapp.dto.RegisterUserRequest;
import com.tweetapp.entity.AppUser;
import com.tweetapp.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.tweetapp.constants.AppConstants.REGISTERED_SUCCESSFULLY;
import static com.tweetapp.constants.AppConstants.ROOT_URL;

@Slf4j
@RestController
@RequestMapping(path = ROOT_URL)
public class UserController {

    @Autowired
    AppUserService appUserService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterUserRequest registerRequest){
        log.info("Register user request for {} {} with userId : {}",
                registerRequest.getFirstName(),registerRequest.getLastName(),registerRequest.getUserId());
        appUserService.register(registerRequest);
        return ResponseEntity.ok(String.format(REGISTERED_SUCCESSFULLY, registerRequest.getUserId()));
    }

    @GetMapping("/login")
    public ResponseEntity<JwtTokenResponse> login(@RequestParam String username, @RequestParam String password){
        log.info("Login request for user : {}",username);
        return ResponseEntity.ok(appUserService.login(username,password));
    }

    @GetMapping("/{username}/forgot")
    public ResponseEntity<Object> forgotPassword(@PathVariable String username,@RequestParam String password){
        log.info("Forgot Password request for user: {}",username);
        return ResponseEntity.ok(appUserService.forgotPassword(username,password));
    }

    @GetMapping("/users")
    public ResponseEntity<AppUsers> getAllUser(){
        log.info("Get All user request");
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @GetMapping("/user/search/{username}")
    public ResponseEntity<List<AppUser>> searchUsers(@PathVariable String username){
        log.info("Search request for user with username like {}",username);
        return ResponseEntity.ok(appUserService.searchUser(username));
    }
}

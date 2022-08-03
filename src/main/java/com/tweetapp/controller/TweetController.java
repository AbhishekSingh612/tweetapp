package com.tweetapp.controller;

import com.tweetapp.dto.ReplyRequest;
import com.tweetapp.dto.TweetRequest;
import com.tweetapp.entity.Tweet;
import com.tweetapp.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.tweetapp.constants.AppConstants.ROOT_URL;

@Slf4j
@RestController
@RequestMapping(path = ROOT_URL)
public class TweetController {

    @Autowired
    TweetService tweetService;

    @PostMapping("/{username}/add")
    public ResponseEntity<Tweet> postTweet(@RequestBody @Valid TweetRequest tweetRequest, @PathVariable String username, Principal principal) {
        log.info("Create tweet request by : {}", username);
        return ResponseEntity.ok(tweetService.postTweet(tweetRequest, username, principal));
    }

    @PutMapping("/{username}/update/{id}")
    public ResponseEntity<Object> updateTweet(@PathVariable String username, @PathVariable String id, @RequestBody @Valid TweetRequest tweetRequest, Principal principal) {
        log.info("Update tweet with id {} request by user {}", id, username);
        return ResponseEntity.ok(tweetService.updateTweet(username, id, tweetRequest, principal));
    }

    @DeleteMapping("/{username}/delete/{id}")
    public ResponseEntity<Object> deleteTweet(@PathVariable String username, @PathVariable String id, Principal principal) {
        return ResponseEntity.ok(tweetService.deleteTweetById(id,username,principal));
    }

    @PutMapping("/{username}/like/{id}")
    public ResponseEntity<Object> likeTweet(@PathVariable String username, @PathVariable String id, Principal principal) {
        return ResponseEntity.ok(tweetService.likeTweet(username,id,principal));
    }

    @PostMapping("/{username}/reply/{id}")
    public ResponseEntity<Object> replyTweet(@PathVariable String username, @PathVariable String id, @RequestBody @Valid ReplyRequest replyRequest , Principal principal) {
        return ResponseEntity.ok(tweetService.reply(id,username,replyRequest,principal));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTweets() {
        log.info("Get all tweet request");
        return ResponseEntity.ok(tweetService.getAllTweet());
    }

    @GetMapping("/{username}")
    public ResponseEntity<Object> getUserTweets(@PathVariable String username) {
        log.info("Get all tweets by user with username : {}", username);
        return ResponseEntity.ok(tweetService.getAllTweetByUser(username));
    }
}

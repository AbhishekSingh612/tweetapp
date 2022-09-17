package com.tweetapp.controller;

import com.tweetapp.dto.ReplyRequest;
import com.tweetapp.dto.TweetRequest;
import com.tweetapp.entity.Tweet;
import com.tweetapp.service.TweetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.tweetapp.constants.AppConstants.ROOT_URL;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping(path = ROOT_URL)
@Tag(name = "Tweet", description = "Endpoints for managing tweets")
public class TweetController {

    @Autowired
    TweetService tweetService;

    @PostMapping("/{username}/add")
    @Operation(summary = "Post new tweet", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tweet.class)))})
    public ResponseEntity<Tweet> postTweet(@RequestBody @Valid TweetRequest tweetRequest, @PathVariable String username, Principal principal) {
        log.info("Create tweet request by : {}", username);
        return ResponseEntity.ok(tweetService.postTweet(tweetRequest, username, principal));
    }

    @PutMapping("/{username}/update/{id}")
    @Operation(summary = "Update old tweet", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tweet.class)))})
    public ResponseEntity<Object> updateTweet(@PathVariable String username, @PathVariable String id, @RequestBody @Valid TweetRequest tweetRequest, Principal principal) {
        log.info("Update tweet with id {} request by user {}", id, username);
        return ResponseEntity.ok(tweetService.updateTweet(username, id, tweetRequest, principal));
    }

    @DeleteMapping("/{username}/delete/{id}")
    @Operation(summary = "Delete old tweet", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))})
    public ResponseEntity<Object> deleteTweet(@PathVariable String username, @PathVariable String id, Principal principal) {
        return ResponseEntity.ok(tweetService.deleteTweetById(id,username,principal));
    }

    @PutMapping("/{username}/like/{id}")
    @Operation(summary = "Like a tweet", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tweet.class)))})
    public ResponseEntity<Object> likeTweet(@PathVariable String username, @PathVariable String id, Principal principal) {
        return ResponseEntity.ok(tweetService.likeTweet(username,id,principal));
    }

    @PostMapping("/{username}/reply/{id}")
    @Operation(summary = "Reply to a tweet", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tweet.class)))})
    public ResponseEntity<Object> replyTweet(@PathVariable String username, @PathVariable String id, @RequestBody @Valid ReplyRequest replyRequest , Principal principal) {
        return ResponseEntity.ok(tweetService.reply(id,username,replyRequest,principal));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all tweets", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Tweet.class))))})
    public ResponseEntity<Object> getAllTweets() {
        log.info("Get all tweet request");
        return ResponseEntity.ok(tweetService.getAllTweet());
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get all tweets by a particular user", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Tweet.class))))})
    public ResponseEntity<Object> getUserTweets(@PathVariable String username) {
        log.info("Get all tweets by user with username : {}", username);
        return ResponseEntity.ok(tweetService.getAllTweetByUser(username));
    }
}

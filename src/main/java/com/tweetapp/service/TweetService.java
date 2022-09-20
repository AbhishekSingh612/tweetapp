package com.tweetapp.service;

import com.tweetapp.dto.ReplyRequest;
import com.tweetapp.dto.TweetRequest;
import com.tweetapp.entity.AppUser;
import com.tweetapp.entity.Reply;
import com.tweetapp.entity.Tweet;
import com.tweetapp.exception.CannotDeleteTweetException;
import com.tweetapp.exception.CannotUpdateTweetException;
import com.tweetapp.exception.TweetNotFoundException;
import com.tweetapp.repository.AppUserRepository;
import com.tweetapp.repository.ReplyRepository;
import com.tweetapp.repository.TweetRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

import static com.tweetapp.constants.AppConstants.*;

@Slf4j
@Service
public class TweetService {

    private final AppUserRepository appUserRepository;

    private final TweetRepository tweetRepository;

    private final ReplyRepository replyRepository;

    private final ProducerService producerService;

    //Constructor based dependency injection
    public TweetService(AppUserRepository appUserRepository, TweetRepository tweetRepository,
                        ReplyRepository replyRepository, ProducerService producerService) {
        this.appUserRepository = appUserRepository;
        this.tweetRepository = tweetRepository;
        this.replyRepository = replyRepository;
        this.producerService = producerService;
    }

    public Tweet postTweet(TweetRequest tweetRequest, String username, Principal principal) {
        String currentUser = principal.getName();

        if (!StringUtils.equalsIgnoreCase(currentUser, username)) {
            log.error("Bad Request. Username in request is not correct");
            throw new CannotUpdateTweetException("Bad Request. Username in request is not correct");
        }

        AppUser user = appUserRepository.findByEmailOrUserId(currentUser, currentUser).get();
        Tweet tweet = Tweet.builder()
                .author(user)
                .content(tweetRequest.getContent())
                .tag(tweetRequest.getTag())
                .createdAt(LocalDateTime.now())
                .likedBy(new HashSet<>())
                .replies(new ArrayList<>())
                .build();
        tweet = tweetRepository.save(tweet);

        producerService.sendMessage(tweet);

        return tweet;
    }

    public List<Tweet> getAllTweet() {
        return IterableUtils.toList(tweetRepository.findAll());
    }

    public List<Tweet> getAllTweetByUser(String username) {
        return tweetRepository.findByAuthor(username);
    }

    public Tweet updateTweet(String username, String id, TweetRequest tweetRequest, Principal principal) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);

        if (tweetOptional.isEmpty()) {
            log.error("Tweet with " + id + " Not Found");
            throw new TweetNotFoundException("Tweet with " + id + " Not Found");
        }

        Tweet tweet = tweetOptional.get();

        if (!StringUtils.equalsIgnoreCase(principal.getName(), username)) {
            log.error("Bad Request. Cannot update tweet of other users");
            throw new CannotUpdateTweetException("Bad Request. Cannot update tweet of other users");
        }

        if (tweet.getAuthor() != null && !StringUtils.equalsIgnoreCase(principal.getName(), tweet.getAuthor().getUserId())) {
            log.error("Unauthorized! Cannot update tweet of other users");
            throw new CannotUpdateTweetException("Unauthorized! Cannot update tweet of other users");
        }

        if (tweetRequest.getContent() != null)
            tweet.setContent(tweetRequest.getContent());

        if (tweetRequest.getTag() != null)
            tweet.setTag(tweetRequest.getTag());

        tweet = tweetRepository.save(tweet);

        return tweet;
    }

    public String deleteTweetById(String id, String username, Principal principal) {
        if (!StringUtils.equalsIgnoreCase(principal.getName(), username)) {
            log.error("Bad Request. Cannot update tweet of other users");
            throw new CannotDeleteTweetException("Bad Request. Cannot delete tweet of other users");
        }
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        if (tweetOptional.isEmpty()) {
            log.error(String.format(TWEET_NOT_FOUND, id));
            throw new TweetNotFoundException(String.format(TWEET_NOT_FOUND, id));
        }
        tweetOptional.ifPresent(tweetRepository::delete);
        log.info(String.format(TWEET_DELETED, id));
        return String.format(TWEET_DELETED, id);
    }

    public Tweet likeTweet(String username, String id, Principal principal) {
        username = principal.getName();
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        if (tweetOptional.isEmpty()) {
            log.error(String.format(TWEET_NOT_FOUND, id));
            throw new TweetNotFoundException(String.format(TWEET_NOT_FOUND, id));
        }
        Tweet tweet = tweetOptional.get();

        Set<String> likedBy = tweet.getLikedBy();

        if (likedBy.contains(username)) {

            // If already liked then dislike (remove the like)
            likedBy.remove(username);
            log.info("Likes on tweet after dislike : {}", likedBy.size());

        } else {
            // If not liked then add the like
            likedBy.add(username);
            log.info("Likes on tweet after like : {}", likedBy.size());

        }
        return tweetRepository.save(tweet);
    }

    public Tweet reply(String id, String username, ReplyRequest replyRequest, Principal principal) {
        username = principal.getName();
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);
        if (tweetOptional.isEmpty()) {
            log.error(String.format(TWEET_NOT_FOUND, id));
            throw new TweetNotFoundException(String.format(TWEET_NOT_FOUND, id));
        }

        Optional<AppUser> userOptional = appUserRepository.findById(username);
        if (userOptional.isEmpty()) {
            log.error(String.format(USER_NOT_FOUND, username));
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        }

        Tweet tweet = tweetOptional.get();
        AppUser user = userOptional.get();

        List<Reply> replies = tweet.getReplies();
        log.info("Replies on tweet before : {}", replies.size());

        Reply reply = Reply.builder()
                .comment(replyRequest.getReply())
                .createdAt(LocalDateTime.now())
                .tag(replyRequest.getTag())
                .user(user)
                .build();

        replies.add(replyRepository.save(reply));
        log.info("Replies on tweet after : {}", replies.size());
        return tweetRepository.save(tweet);
    }
}

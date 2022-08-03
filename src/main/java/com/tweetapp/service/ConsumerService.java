package com.tweetapp.service;

import com.tweetapp.entity.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public final class ConsumerService {
    private final static String TOPIC = "tweets";

    @KafkaListener(topics = TOPIC, groupId = "group_id")
    public void consume(Tweet tweet) {
        log.info("Successfully consumed tweet [ {} ] on  kafka topic [ {} ]",tweet, TOPIC);
    }
}
package com.tweetapp.service;

import com.tweetapp.entity.Tweet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public final class ProducerService {
    private static final String TOPIC = "tweets";

    /*@Autowired
    private KafkaTemplate<String, Tweet> kafkaTemplate;*/

    public void sendMessage(Tweet tweet) {
        log.info("=> Publishing Tweet [ {} ] to kafka topic {}", TOPIC, tweet);
        /*this.kafkaTemplate.send(TOPIC, tweet);*/
    }
}
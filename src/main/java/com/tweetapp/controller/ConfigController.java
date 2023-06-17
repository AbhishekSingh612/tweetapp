package com.tweetapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("*")
@RestController
public class ConfigController {

    @GetMapping("/test")
    public ResponseEntity<Object> test(){
        log.info("Test Request Recieved");
        return ResponseEntity.ok("Running");
    }
}

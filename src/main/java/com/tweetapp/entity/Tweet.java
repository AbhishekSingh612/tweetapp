package com.tweetapp.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Document("Tweet")
@NoArgsConstructor
@AllArgsConstructor
public class Tweet {

    @Id
    private String tweetId;

    @DocumentReference
    private AppUser author;

    private String content;

    private String tag;

    private LocalDateTime createdAt;

    private Set<String> likedBy;

    @DocumentReference
    private List<Reply> replies;

}

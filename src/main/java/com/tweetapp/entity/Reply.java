package com.tweetapp.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@Builder
@Document("Reply")
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    @Id
    private String id;

    @DocumentReference
    private AppUser user;

    private String comment;

    private String tag;

    private LocalDateTime createdAt;
}

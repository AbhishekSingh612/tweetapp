package com.tweetapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TweetRequest {

    @NotBlank(message = "Tweet content cannot be blank")
    @Size(max=144, message="Tweet content must be less than 144 characters")
    private String content;

    @Size(max=50, message="Tweet tag must be less than 50 characters")
    private String tag;

}

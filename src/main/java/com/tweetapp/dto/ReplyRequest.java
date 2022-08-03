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
public class ReplyRequest {

    @NotBlank(message = "Reply cannot be blank")
    @Size(max=144, message="Reply must be less than 144 characters")
    private String reply;

    private String tag;
}

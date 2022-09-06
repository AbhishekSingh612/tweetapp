package com.tweetapp.dto;

import com.tweetapp.entity.AppUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtTokenResponse {
    private String token;
    private long expiryDateMs;
    private String user;

    public void setUser(AppUser user) {
        this.user = user.getUserId();
    }
}

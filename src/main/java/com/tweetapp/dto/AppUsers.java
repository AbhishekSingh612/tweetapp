package com.tweetapp.dto;

import com.tweetapp.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@ToString
public class AppUsers {
    List<AppUser> users;
}

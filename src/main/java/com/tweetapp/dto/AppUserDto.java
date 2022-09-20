package com.tweetapp.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
}

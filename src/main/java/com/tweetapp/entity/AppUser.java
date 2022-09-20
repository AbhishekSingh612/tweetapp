package com.tweetapp.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@DynamoDBTable(tableName = "User")
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @DynamoDBHashKey
    private String userId;

    @DynamoDBAttribute
    private String firstName;

    @DynamoDBAttribute
    private String lastName;

    //@Indexed(unique = true)
    @DynamoDBAttribute
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    private String contactNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @DynamoDBAttribute
    private List<String> roles;
}

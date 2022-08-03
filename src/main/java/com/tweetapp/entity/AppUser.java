package com.tweetapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document("User")
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    private String userId;

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String contactNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<String> roles;
}

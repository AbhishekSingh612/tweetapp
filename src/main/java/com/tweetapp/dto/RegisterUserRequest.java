package com.tweetapp.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ToString
public class RegisterUserRequest {

    @NotBlank(message = "User Id field is required ")
    private String userId;

    @NotBlank(message = "First Name field is required ")
    private String firstName;

    @NotBlank(message = "LastName field is required ")
    private String lastName;

    @Email
    @NotBlank(message = "Email field is required ")
    private String email;

    @NotBlank(message = "Password field is required ")
    private String password;

    @NotBlank(message = "Confirm Password field is required ")
    private String confirmPassword;

    @Pattern(regexp = "^[0-9]{10}$",message = "Contact Number should contain 10 Digits")
    private String contactNumber;
}

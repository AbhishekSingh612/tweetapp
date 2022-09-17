package com.tweetapp.controller;

import com.tweetapp.dto.AppUsers;
import com.tweetapp.dto.JwtTokenResponse;
import com.tweetapp.dto.RegisterUserRequest;
import com.tweetapp.entity.AppUser;
import com.tweetapp.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static com.tweetapp.constants.AppConstants.REGISTERED_SUCCESSFULLY;
import static com.tweetapp.constants.AppConstants.ROOT_URL;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(path = ROOT_URL)
@Tag(name = "Users", description = "Endpoints for managing users")
public class UserController {

    @Autowired
    AppUserService appUserService;

    @PostMapping("/register")
    @Operation(summary = "Register new user", responses = {@ApiResponse(description = "Successfully registered", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))})
    public ResponseEntity<Object> registerUser(@Valid @RequestBody RegisterUserRequest registerRequest) {
        log.info("Register user request for {} {} with userId : {}",
                registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getUserId());
        appUserService.register(registerRequest);
        return ResponseEntity.ok(String.format(REGISTERED_SUCCESSFULLY, registerRequest.getUserId()));
    }

    @GetMapping("/login")
    @Operation(summary = "Authenticate user and retrieve jwt token on success", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtTokenResponse.class)))})
    public ResponseEntity<JwtTokenResponse> login(@RequestParam String username, @RequestParam String password) {
        log.info("Login request for user : {}", username);
        return ResponseEntity.ok(appUserService.login(username, password));
    }

    @GetMapping("/{username}/forgot")
    @Operation(summary = "Reset user password", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))})
    public ResponseEntity<Object> forgotPassword(@PathVariable String username, @RequestParam String password) {
        log.info("Forgot Password request for user: {}", username);
        return ResponseEntity.ok(appUserService.forgotPassword(username, password));
    }

    @GetMapping("/users")
    @Operation(summary = "Get all users", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppUsers.class)))})
    public ResponseEntity<AppUsers> getAllUser() {
        log.info("Get All user request");
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    @GetMapping("/user/search/{username}")
    @Operation(summary = "Search user with username starting with provided text", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppUsers.class)))})
    public ResponseEntity<AppUsers> searchUsers(@PathVariable String username) {
        log.info("Search request for user with username like {}", username);
        return ResponseEntity.ok(appUserService.searchUser(username));
    }

    @GetMapping("/user/currentUserDetail")
    @Operation(summary = "Get current user details", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppUser.class)))})
    public ResponseEntity<AppUser> getCurrentUserDetails(Principal principal) {
        log.info("Get Current user request");
        return ResponseEntity.ok(appUserService.getcurrentUserDetails(principal));
    }

    @GetMapping("/user/find/{username}")
    @Operation(summary = "Check if username exists", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppUser.class)))})
    public ResponseEntity<AppUser> getUserDetails(@PathVariable String username) {
        log.info("Get user request");
        return ResponseEntity.ok(appUserService.getUserDetails(username));
    }

    @GetMapping("/user/findEmail/{email}")
    @Operation(summary = "Check if email exists", responses = {@ApiResponse(description = "Success", responseCode = "200",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AppUser.class)))})
    public ResponseEntity<AppUser> getUserDetailsWithEmail(@PathVariable String email) {
        log.info("Get user request");
        return ResponseEntity.ok(appUserService.getUserDetailsWithEmail(email));
    }
}

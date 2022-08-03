package com.tweetapp.service;

import com.tweetapp.dto.AppUsers;
import com.tweetapp.dto.JwtTokenResponse;
import com.tweetapp.dto.RegisterUserRequest;
import com.tweetapp.entity.AppUser;
import com.tweetapp.exception.ConfirmPasswordMismatchException;
import com.tweetapp.exception.InvalidUsernameOrPasswordException;
import com.tweetapp.exception.UserAlreadyExistException;
import com.tweetapp.repository.AppUserRepository;
import com.tweetapp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.tweetapp.constants.AppConstants.*;

@Slf4j
@Service
public class AppUserService {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    UserDetailsService userDetailService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    public void register(RegisterUserRequest request) {
        log.debug(START_OF_METHOD_IN_AUTH_SERVICE, "register");

        validateRegisterRequest(request);

        AppUser user = AppUser.builder()
                .userId(request.getUserId())
                .email(request.getEmail())
                .contactNumber(request.getContactNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(encoder.encode(request.getPassword()))
                .roles(List.of("USER"))
                .build();

        appUserRepository.save(user);
        log.debug(END_OF_METHOD_IN_AUTH_SERVICE, "register");
    }

    private void validateRegisterRequest(RegisterUserRequest request) {
        if (isUserPresent(request.getEmail(), request.getUserId()))
            throw new UserAlreadyExistException("User already Exists");
        if (isNotValidPassword(request))
            throw new ConfirmPasswordMismatchException("Confirm password does not match");
        log.info("User register request is valid");
    }

    private boolean isNotValidPassword(RegisterUserRequest request) {
        return !request.getPassword().equalsIgnoreCase(request.getConfirmPassword());
    }

    private boolean isUserPresent(String username, String email) {
        return appUserRepository.findByEmailOrUserId(email, username).isPresent();
    }

    public JwtTokenResponse login(String username, String password) {
        log.debug(START_OF_METHOD_IN_AUTH_SERVICE, "login");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (AuthenticationException e) {
            log.error("Invalid Login details");
            throw new InvalidUsernameOrPasswordException("Username/Password is invalid");
        }
        log.debug(END_OF_METHOD_IN_AUTH_SERVICE, "login");
        String token = jwtUtil.generateToken(userDetailService.loadUserByUsername(username));
        return JwtTokenResponse.builder().token(token).build();
    }

    public String forgotPassword(String username, String password) throws UsernameNotFoundException {
        log.debug(START_OF_METHOD_IN_AUTH_SERVICE, "forgotPassword");
        Optional<AppUser> userOptional = appUserRepository.findByEmailOrUserId(username, username);
        if (userOptional.isEmpty()) {
            log.error("Username/Email not found!");
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
        }
        AppUser user = userOptional.get();
        user.setPassword(encoder.encode(password));
        appUserRepository.save(user);
        log.debug(END_OF_METHOD_IN_AUTH_SERVICE, "forgotPassword");
        return String.format(PASSWORD_CHANGED_SUCCESSFULLY, username);
    }

    public AppUsers getAllUsers() {
        List<AppUser> users = appUserRepository.findAll();
        return AppUsers.builder().users(users).build();
    }

    public List<AppUser> searchUser(String username) {
        List<AppUser> users = appUserRepository.findByEmailOrUserIdRegex(username);
        if (users.isEmpty()){
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        return users;
    }
}

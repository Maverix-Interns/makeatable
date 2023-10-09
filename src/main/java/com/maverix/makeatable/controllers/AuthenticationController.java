package com.maverix.makeatable.controllers;
import com.maverix.makeatable.dto.User.UserRegistrationDto;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.models.VerificationToken;
import com.maverix.makeatable.services.AuthService;
import com.maverix.makeatable.services.VerificationTokenService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

//TODO
public class AuthenticationController {

    private final AuthService authService;
    private final VerificationTokenService verificationTokenService;

    public AuthenticationController(AuthService authService, VerificationTokenService verificationTokenService) {
        this.authService = authService;
        this.verificationTokenService = verificationTokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<String>> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        User registeredUser = authService.registerUser(registrationDto);
        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(registeredUser);

        // TODO Send verification email (implement this part based on your requirements)
      // todo migrate to security package
        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User registered successfully. Please check your email for verification.")
                .data("Verification token: ")  // Include token if needed)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<Response<String>> verifyUser(@RequestParam("token") String token) {
        User user = verificationTokenService.verifyUser(token);
//todo migrate to security package

        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User verified successfully.")
                .data("Verified user: " + user.getEmail())  // Include user info if needed
                .build();

        return ResponseEntity.ok(response);
    }
}

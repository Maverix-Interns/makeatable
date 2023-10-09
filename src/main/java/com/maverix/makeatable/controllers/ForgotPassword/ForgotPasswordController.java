package com.maverix.makeatable.controllers.ForgotPassword;

import com.maverix.makeatable.dto.User.ForgotPasswordRequestDto;
import com.maverix.makeatable.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final UserService userService;

    public ForgotPasswordController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> requestResetPassword(@RequestBody ForgotPasswordRequestDto requestDTO) {
        String email = requestDTO.getEmail();

        // Generate a unique token (UUID) and save it in the database with the email
        String resetToken = UUID.randomUUID().toString();

        userService.createPasswordResetTokenForUser(email, resetToken);

        // todo Send an email to the user with the reset link
        String resetLink = "https://yourwebsite.com/reset-password?token=" + resetToken;


        return ResponseEntity.ok("Password reset link sent to your email."+resetToken);
    }
}
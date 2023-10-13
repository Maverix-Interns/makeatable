package com.maverix.makeatable.config.Security;
import com.maverix.makeatable.config.Mail.MailSender;
import com.maverix.makeatable.config.Security.Dto.AuthenticationRequest;
import com.maverix.makeatable.config.Security.Dto.AuthenticationResponse;
import com.maverix.makeatable.dto.User.UserRegistrationDto;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.models.VerificationToken;
import com.maverix.makeatable.services.VerificationTokenService;
import com.maverix.makeatable.util.JwtUtils;
import com.maverix.makeatable.util.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("auth")

public class AuthController {
    private final AuthenticationService authenticationService;
    private final HttpServletRequest request;
    private final JwtUtils jwtUtils;
    private final AuthService authService;
    private final VerificationTokenService verificationTokenService;
    private final MailSender mailSender;


    public AuthController(AuthenticationService authenticationService, HttpServletRequest request, JwtUtils jwtUtils,
                          AuthService authService, VerificationTokenService verificationTokenService, MailSender mailSender) {
        this.authenticationService = authenticationService;
        this.request = request;
        this.jwtUtils = jwtUtils;
        this.authService = authService;
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<String>> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        User registeredUser = authService.registerUser(registrationDto);
        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(registeredUser);
        mailSender.sendVerificationEmail(registeredUser.getEmail(), verificationToken.getToken());

        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User registered successfully. Please check your email for verification.")
                .data("Verification token: ")
                .build();
        return ResponseEntity.ok(response);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request)
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyUser(@PathVariable String token) {
        User user = verificationTokenService.verifyUser(token);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.GONE).body("Verification token is expired or invalid.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("User successfully verified.");
    }

}

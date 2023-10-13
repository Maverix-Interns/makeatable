package com.maverix.makeatable.config.Security;

import com.maverix.makeatable.config.Security.Dto.AuthenticationRequest;
import com.maverix.makeatable.config.Security.Dto.AuthenticationResponse;
import com.maverix.makeatable.config.Security.Dto.RegisterRequest;
import com.maverix.makeatable.dto.User.UserRegistrationDto;
import com.maverix.makeatable.enums.UserStatus;
import com.maverix.makeatable.exceptions.DuplicateEmailException;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public User registerUser(UserRegistrationDto registrationDto) {
        try {
            User user = new User();
            user.setFullName(registrationDto.getFullName());
            user.setEmail(registrationDto.getEmail());
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            user.setMobileNum(registrationDto.getMobileNumber());
            user.setPreference(registrationDto.getPreference());
            user.setUserType(registrationDto.getUserType());

            user.setUserStatus(UserStatus.PENDING);
            user.setUpdatedAt(LocalDateTime.now());
            user.setCreatedAt(LocalDateTime.now());

            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException("Email already exists: " + registrationDto.getEmail());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during registration.", e);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Boolean validateToken(String jwtFromRequest) {
      return  jwtService.isTokenExpired(jwtFromRequest);
    }
}

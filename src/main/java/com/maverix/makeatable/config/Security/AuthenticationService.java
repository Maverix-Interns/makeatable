package com.maverix.makeatable.config.Security;

import com.maverix.makeatable.config.Security.Dto.AuthenticationRequest;
import com.maverix.makeatable.config.Security.Dto.AuthenticationResponse;
import com.maverix.makeatable.config.Security.Dto.RegisterRequest;
import com.maverix.makeatable.enums.UserStatus;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .mobileNum(request.getMobileNumber())
                .preference(request.getPreference())
                .userType(request.getUserType())
                .userStatus(UserStatus.PENDING)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
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

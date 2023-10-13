package com.maverix.makeatable.config.Security;

import com.maverix.makeatable.dto.User.UserRegistrationDto;
import com.maverix.makeatable.enums.UserStatus;
import com.maverix.makeatable.exceptions.DuplicateEmailException;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;
    }


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
}

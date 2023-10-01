package com.maverix.makeatable.services;

import com.maverix.makeatable.dto.User.UserRegistrationDto;
import com.maverix.makeatable.enums.UserStatus;
import com.maverix.makeatable.exceptions.DuplicateEmailException;
import com.maverix.makeatable.models.User;
import com.maverix.makeatable.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

//TODO
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public User registerUser(UserRegistrationDto registrationDto) {
        try {
            User user = new User();
            user.setFullName(registrationDto.getFullName());
            user.setEmail(registrationDto.getEmail());
            user.setPassword(registrationDto.getPassword());
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

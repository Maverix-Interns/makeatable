package com.maverix.makeatable.controllers;

import com.maverix.makeatable.dto.User.UserGetDto;
import com.maverix.makeatable.dto.User.CustomerProfileUpdateDto;
import com.maverix.makeatable.services.CustomerProfileService;
import com.maverix.makeatable.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/customer-profile")
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;

    @Autowired
    public CustomerProfileController(CustomerProfileService customerProfileService) {
        this.customerProfileService = customerProfileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserGetDto>> getUserProfile(@PathVariable Long id) {
        UserGetDto usergetDto = customerProfileService.getUserProfile(id);

        Response<UserGetDto> response = Response.<UserGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .data(usergetDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{email}")
    public ResponseEntity<Response<String>> updateUserProfile(@PathVariable String email, @RequestBody CustomerProfileUpdateDto updateDto) {
        customerProfileService.updateUserProfile(email, updateDto);

        Response<String> response = Response.<String>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User profile updated successfully.")
                .build();

        return ResponseEntity.ok(response);
    }
}

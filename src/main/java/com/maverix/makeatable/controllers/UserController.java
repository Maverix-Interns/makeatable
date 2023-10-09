package com.maverix.makeatable.controllers;

import com.maverix.makeatable.dto.User.UserGetDto;
import com.maverix.makeatable.dto.User.UserPostDto;
import com.maverix.makeatable.dto.User.UserPutDto;
import com.maverix.makeatable.services.UserService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Response<List<UserGetDto>>> getAllUsers() {
        List<UserGetDto> users = userService.getAllUsers();
        Response<List<UserGetDto>> response = Response.<List<UserGetDto>>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Users retrieved successfully")
                .data(users)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserGetDto>> getUserById(@PathVariable Long id) {
        UserGetDto user = userService.getUserById(id);
        Response<UserGetDto> response = Response.<UserGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User retrieved successfully")
                .data(user)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<UserGetDto>> createUser(@RequestBody UserPostDto userPostDto) {

        UserGetDto createdUser = userService.createUser(userPostDto);

        Response<UserGetDto> response = Response.<UserGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("User created successfully")
                .data(createdUser)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UserGetDto>> updateUser(@PathVariable Long id, @RequestBody UserPutDto userPutDto) {

        UserGetDto updatedUser = userService.updateUser(id, userPutDto);

        Response<UserGetDto> response = Response.<UserGetDto>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("User updated successfully")
                .data(updatedUser)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        Response<Void> response = Response.<Void>builder()
                .timeStamp(LocalDateTime.now())
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("User deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}

package com.skypay.hotelreservation.controllers;

import com.skypay.hotelreservation.domain.dto.request.UserRequest;
import com.skypay.hotelreservation.domain.dto.response.UserResponse;
import com.skypay.hotelreservation.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createOrUpdateUser(@Valid @RequestBody UserRequest request) {
        log.info("POST /api/users - Creating/updating user {}", request.getUserId());
        UserResponse response = userService.createOrUpdateUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("GET /api/users - Fetching all users");
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId) {
        log.info("GET /api/users/{} - Fetching user", userId);
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }
}

package com.skypay.hotelreservation.services.impl;

import com.skypay.hotelreservation.domain.dto.request.UserRequest;
import com.skypay.hotelreservation.domain.dto.response.UserResponse;
import com.skypay.hotelreservation.domain.entities.User;
import com.skypay.hotelreservation.repository.UserRepository;
import com.skypay.hotelreservation.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse createOrUpdateUser(UserRequest request) {
        log.info("Creating or updating user with ID: {}", request.getUserId());

        User user = userRepository.findByUserId(request.getUserId())
                .orElse(User.builder()
                        .userId(request.getUserId())
                        .build());

        user.setBalance(request.getBalance());

        User savedUser = userRepository.save(user);
        log.info("User {} saved successfully with balance: {}", savedUser.getUserId(), savedUser.getBalance());

        return mapToResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer userId) {
        log.debug("Fetching user by ID: {}", userId);
        return userRepository.findByUserId(userId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .balance(user.getBalance())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

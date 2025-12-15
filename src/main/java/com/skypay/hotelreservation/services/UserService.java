package com.skypay.hotelreservation.services;

import com.skypay.hotelreservation.domain.dto.request.UserRequest;
import com.skypay.hotelreservation.domain.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createOrUpdateUser(UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Integer userId);
}

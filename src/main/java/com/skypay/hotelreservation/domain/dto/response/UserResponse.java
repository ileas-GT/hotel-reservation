package com.skypay.hotelreservation.domain.dto.response;


import lombok.Data;

import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private Integer userId;
    private Integer balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

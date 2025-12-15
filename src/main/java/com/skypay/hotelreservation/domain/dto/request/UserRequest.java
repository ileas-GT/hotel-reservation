package com.skypay.hotelreservation.domain.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequest {
    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Balance is required")
    @Min(value = 0, message = "Balance must be positive")
    private Integer balance;
}

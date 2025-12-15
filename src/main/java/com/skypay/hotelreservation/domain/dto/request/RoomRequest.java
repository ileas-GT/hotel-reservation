package com.skypay.hotelreservation.domain.dto.request;

import com.skypay.hotelreservation.domain.enums.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomRequest {
    @NotNull(message = "Room number is required")
    private Integer roomNumber;

    @NotNull(message = "Room type is required")
    private RoomType roomType;

    @NotNull(message = "Price per night is required")
    @Min(value = 0, message = "Price must be positive")
    private Integer pricePerNight;
}

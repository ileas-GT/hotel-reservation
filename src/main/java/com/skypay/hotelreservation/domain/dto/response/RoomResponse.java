package com.skypay.hotelreservation.domain.dto.response;

import com.skypay.hotelreservation.domain.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RoomResponse {
    private Long id;
    private Integer roomNumber;
    private RoomType roomType;
    private Integer pricePerNight;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

package com.skypay.hotelreservation.domain.dto.response;

import com.skypay.hotelreservation.domain.enums.RoomType;
import lombok.Data;

import java.time.LocalDate;

import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {
    private Long id;
    private Integer userId;
    private Integer userBalanceAtBooking;
    private Integer roomNumber;
    private RoomType roomTypeAtBooking;
    private Integer pricePerNightAtBooking;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Integer totalCost;
    private LocalDateTime createdAt;
}

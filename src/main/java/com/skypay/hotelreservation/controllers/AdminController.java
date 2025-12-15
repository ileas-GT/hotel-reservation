package com.skypay.hotelreservation.controllers;

import com.skypay.hotelreservation.domain.dto.response.BookingResponse;
import com.skypay.hotelreservation.domain.dto.response.RoomResponse;
import com.skypay.hotelreservation.domain.dto.response.UserResponse;
import com.skypay.hotelreservation.services.BookingService;
import com.skypay.hotelreservation.services.RoomService;
import com.skypay.hotelreservation.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final RoomService roomService;
    private final UserService userService;
    private final BookingService bookingService;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllData() {
        log.info("GET /api/admin/all - Fetching all system data");

        List<RoomResponse> rooms = roomService.getAllRooms();
        List<UserResponse> users = userService.getAllUsers();
        List<BookingResponse> bookings = bookingService.getAllBookings();

        Map<String, Object> response = new HashMap<>();
        response.put("rooms", rooms);
        response.put("users", users);
        response.put("bookings", bookings);

        return ResponseEntity.ok(response);
    }
}

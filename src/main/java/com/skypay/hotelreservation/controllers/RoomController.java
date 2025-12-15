package com.skypay.hotelreservation.controllers;


import com.skypay.hotelreservation.domain.dto.request.RoomRequest;
import com.skypay.hotelreservation.domain.dto.response.RoomResponse;
import com.skypay.hotelreservation.services.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> createOrUpdateRoom(@Valid @RequestBody RoomRequest request) {
        log.info("POST /api/rooms - Creating/updating room {}", request.getRoomNumber());
        RoomResponse response = roomService.createOrUpdateRoom(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        log.info("GET /api/rooms - Fetching all rooms");
        List<RoomResponse> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<RoomResponse> getRoomByNumber(@PathVariable Integer roomNumber) {
        log.info("GET /api/rooms/{} - Fetching room", roomNumber);
        RoomResponse response = roomService.getRoomByNumber(roomNumber);
        return ResponseEntity.ok(response);
    }
}

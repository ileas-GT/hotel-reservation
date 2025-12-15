package com.skypay.hotelreservation.services.impl;

import com.skypay.hotelreservation.domain.dto.request.RoomRequest;
import com.skypay.hotelreservation.domain.dto.response.RoomResponse;
import com.skypay.hotelreservation.domain.entities.Room;
import com.skypay.hotelreservation.repository.RoomRepository;
import com.skypay.hotelreservation.services.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public RoomResponse createOrUpdateRoom(RoomRequest request) {
        log.info("Creating or updating room with number: {}", request.getRoomNumber());

        Room room = roomRepository.findByRoomNumber(request.getRoomNumber())
                .orElse(Room.builder()
                        .roomNumber(request.getRoomNumber())
                        .build());

        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());

        Room savedRoom = roomRepository.save(room);
        log.info("Room {} saved successfully", savedRoom.getRoomNumber());

        return mapToResponse(savedRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> getAllRooms() {
        log.debug("Fetching all rooms");
        return roomRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse getRoomByNumber(Integer roomNumber) {
        log.debug("Fetching room by number: {}", roomNumber);
        return roomRepository.findByRoomNumber(roomNumber)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomNumber));
    }

    private RoomResponse mapToResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .pricePerNight(room.getPricePerNight())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();
    }
}

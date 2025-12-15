package com.skypay.hotelreservation.services;

import com.skypay.hotelreservation.domain.dto.request.RoomRequest;
import com.skypay.hotelreservation.domain.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {
    RoomResponse createOrUpdateRoom(RoomRequest request);
    List<RoomResponse> getAllRooms();
    RoomResponse getRoomByNumber(Integer roomNumber);
}

package com.skypay.hotelreservation.services.impl;

import com.skypay.hotelreservation.domain.dto.request.BookingRequest;
import com.skypay.hotelreservation.domain.dto.response.BookingResponse;
import com.skypay.hotelreservation.domain.entities.Booking;
import com.skypay.hotelreservation.domain.entities.Room;
import com.skypay.hotelreservation.domain.entities.User;
import com.skypay.hotelreservation.repository.BookingRepository;
import com.skypay.hotelreservation.repository.RoomRepository;
import com.skypay.hotelreservation.repository.UserRepository;
import com.skypay.hotelreservation.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        log.info("Creating booking for user {} and room {} from {} to {}",
                request.getUserId(), request.getRoomNumber(), request.getCheckIn(), request.getCheckOut());

        // Validate dates
        if (!request.getCheckOut().isAfter(request.getCheckIn())) {
            log.error("Invalid dates: check-out must be after check-in");
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        // Find user
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found: {}", request.getUserId());
                    return new RuntimeException("User not found: " + request.getUserId());
                });

        // Find room
        Room room = roomRepository.findByRoomNumber(request.getRoomNumber())
                .orElseThrow(() -> {
                    log.error("Room not found: {}", request.getRoomNumber());
                    return new RuntimeException("Room not found: " + request.getRoomNumber());
                });

        // Calculate nights and cost
        long nights = ChronoUnit.DAYS.between(request.getCheckIn(), request.getCheckOut());
        int totalCost = (int) nights * room.getPricePerNight();

        log.debug("Booking details: {} nights, total cost: {}", nights, totalCost);

        // Check user balance
        if (user.getBalance() < totalCost) {
            log.error("Insufficient balance for user {}: has {}, needs {}",
                    user.getUserId(), user.getBalance(), totalCost);
            throw new RuntimeException("Insufficient balance");
        }

        // Check room availability
        boolean hasOverlap = bookingRepository.existsOverlappingBooking(
                request.getRoomNumber(), request.getCheckIn(), request.getCheckOut());

        if (hasOverlap) {
            log.error("Room {} is not available for the specified period", request.getRoomNumber());
            throw new RuntimeException("Room not available for specified period");
        }

        // Create booking snapshot
        Booking booking = Booking.builder()
                .userId(user.getUserId())
                .userBalanceAtBooking(user.getBalance())
                .roomNumber(room.getRoomNumber())
                .roomTypeAtBooking(room.getRoomType())
                .pricePerNightAtBooking(room.getPricePerNight())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .totalCost(totalCost)
                .build();

        // Deduct balance
        user.setBalance(user.getBalance() - totalCost);
        userRepository.save(user);

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created successfully with ID: {}", savedBooking.getId());

        return mapToResponse(savedBooking);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getAllBookings() {
        log.debug("Fetching all bookings");
        return bookingRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private BookingResponse mapToResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .userId(booking.getUserId())
                .userBalanceAtBooking(booking.getUserBalanceAtBooking())
                .roomNumber(booking.getRoomNumber())
                .roomTypeAtBooking(booking.getRoomTypeAtBooking())
                .pricePerNightAtBooking(booking.getPricePerNightAtBooking())
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .totalCost(booking.getTotalCost())
                .createdAt(booking.getCreatedAt())
                .build();
    }
}

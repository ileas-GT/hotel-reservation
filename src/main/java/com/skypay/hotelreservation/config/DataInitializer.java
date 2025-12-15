package com.skypay.hotelreservation.config;

import com.skypay.hotelreservation.domain.dto.request.BookingRequest;
import com.skypay.hotelreservation.domain.dto.request.RoomRequest;
import com.skypay.hotelreservation.domain.dto.request.UserRequest;
import com.skypay.hotelreservation.domain.enums.RoomType;
import com.skypay.hotelreservation.services.BookingService;
import com.skypay.hotelreservation.services.RoomService;
import com.skypay.hotelreservation.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(RoomService roomService,
                                   UserService userService,
                                   BookingService bookingService) {
        return args -> {
            log.info("=== Initializing test data ===");

            // Create rooms
            RoomRequest room1 = new RoomRequest();
            room1.setRoomNumber(1);
            room1.setRoomType(RoomType.STANDARD);
            room1.setPricePerNight(1000);
            roomService.createOrUpdateRoom(room1);

            RoomRequest room2 = new RoomRequest();
            room2.setRoomNumber(2);
            room2.setRoomType(RoomType.JUNIOR);
            room2.setPricePerNight(2000);
            roomService.createOrUpdateRoom(room2);

            RoomRequest room3 = new RoomRequest();
            room3.setRoomNumber(3);
            room3.setRoomType(RoomType.SUITE);
            room3.setPricePerNight(3000);
            roomService.createOrUpdateRoom(room3);

            // Create users
            UserRequest user1 = new UserRequest();
            user1.setUserId(1);
            user1.setBalance(5000);
            userService.createOrUpdateUser(user1);

            UserRequest user2 = new UserRequest();
            user2.setUserId(2);
            user2.setBalance(10000);
            userService.createOrUpdateUser(user2);

            // Test bookings
            try {
                BookingRequest booking1 = new BookingRequest();
                booking1.setUserId(1);
                booking1.setRoomNumber(2);
                booking1.setCheckIn(LocalDate.of(2026, 6, 30));
                booking1.setCheckOut(LocalDate.of(2026, 7, 7));
                bookingService.createBooking(booking1);
                log.info("✓ Booking 1 successful");
            } catch (Exception e) {
                log.error("✗ Booking 1 failed: {}", e.getMessage());
            }

            try {
                BookingRequest booking2 = new BookingRequest();
                booking2.setUserId(1);
                booking2.setRoomNumber(2);
                booking2.setCheckIn(LocalDate.of(2026, 7, 7));
                booking2.setCheckOut(LocalDate.of(2026, 6, 30));
                bookingService.createBooking(booking2);
                log.info("✓ Booking 2 successful");
            } catch (Exception e) {
                log.error("✗ Booking 2 failed: {}", e.getMessage());
            }

            try {
                BookingRequest booking3 = new BookingRequest();
                booking3.setUserId(1);
                booking3.setRoomNumber(1);
                booking3.setCheckIn(LocalDate.of(2026, 7, 7));
                booking3.setCheckOut(LocalDate.of(2026, 7, 8));
                bookingService.createBooking(booking3);
                log.info("✓ Booking 3 successful");
            } catch (Exception e) {
                log.error("✗ Booking 3 failed: {}", e.getMessage());
            }

            try {
                BookingRequest booking4 = new BookingRequest();
                booking4.setUserId(2);
                booking4.setRoomNumber(1);
                booking4.setCheckIn(LocalDate.of(2026, 7, 7));
                booking4.setCheckOut(LocalDate.of(2026, 7, 9));
                bookingService.createBooking(booking4);
                log.info("✓ Booking 4 successful");
            } catch (Exception e) {
                log.error("✗ Booking 4 failed: {}", e.getMessage());
            }

            try {
                BookingRequest booking5 = new BookingRequest();
                booking5.setUserId(2);
                booking5.setRoomNumber(3);
                booking5.setCheckIn(LocalDate.of(2026, 7, 7));
                booking5.setCheckOut(LocalDate.of(2026, 7, 8));
                bookingService.createBooking(booking5);
                log.info("✓ Booking 5 successful");
            } catch (Exception e) {
                log.error("✗ Booking 5 failed: {}", e.getMessage());
            }

            // Update room 1
            RoomRequest updateRoom1 = new RoomRequest();
            updateRoom1.setRoomNumber(1);
            updateRoom1.setRoomType(RoomType.SUITE);
            updateRoom1.setPricePerNight(10000);
            roomService.createOrUpdateRoom(updateRoom1);

            log.info("=== Test data initialization complete ===");
        };
    }
}

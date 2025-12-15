package com.skypay.hotelreservation.repository;

import com.skypay.hotelreservation.domain.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.roomNumber = :roomNumber " +
            "AND NOT (b.checkOut <= :checkIn OR b.checkIn >= :checkOut)")
    boolean existsOverlappingBooking(@Param("roomNumber") Integer roomNumber,
                                     @Param("checkIn") LocalDate checkIn,
                                     @Param("checkOut") LocalDate checkOut);
}
package com.skypay.hotelreservation.services;

import com.skypay.hotelreservation.domain.dto.request.BookingRequest;
import com.skypay.hotelreservation.domain.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    List<BookingResponse> getAllBookings();
}

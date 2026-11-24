package com.bush.skygate.booking;


import com.bush.skygate.booking.dto.BookingResponse;
import com.bush.skygate.flight.FlightController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * {@code GET /api/v1/booking/{publicId}}: returns full details of one booking.
     *
     * @param publicId the externally visible UUID of the booking (not the database id)
     * @return the booking as a DTO
     */
    @GetMapping("/{publicId}")
    public BookingResponse getByPublicId(
            @PathVariable UUID publicId){
        return bookingService.getByPublicId(publicId);
    }
}

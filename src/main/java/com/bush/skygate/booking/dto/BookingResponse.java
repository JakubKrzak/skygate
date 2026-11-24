package com.bush.skygate.booking.dto;

import com.bush.skygate.booking.BookingLanguage;
import com.bush.skygate.booking.BookingStatus;

import java.util.UUID;

public record BookingResponse(
        UUID publicId,
        String bookingReference,
        BookingStatus status,
        String channel,
        String email,
        String contactName,
        String contactPhone,
        BookingLanguage language
) {
}

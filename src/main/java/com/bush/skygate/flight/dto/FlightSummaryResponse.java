package com.bush.skygate.flight.dto;

import java.time.LocalDate;
import java.util.UUID;

public record FlightSummaryResponse(
        UUID publicId,
        String flightNumber,
        String origin,
        String destination,
        LocalDate scheduledDepartureDate
) {
}

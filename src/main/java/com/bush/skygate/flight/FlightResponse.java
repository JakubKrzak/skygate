package com.bush.skygate.flight;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record FlightResponse(
        UUID publicId,
        String flightNumber,
        String origin,
        String destination,
        Short capacity,
        LocalDate scheduledDepartureDate,
        Instant estimatedDepartureAt,
        String gate,
        FlightStatus status,
        Instant gateClosesAt,
        Instant boardingAt
){}
package com.bush.skygate.flight.dto;

import com.bush.skygate.flight.FlightStatus;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.time.LocalDate;


public record FlightCreate(
        @NotBlank(message = "flight number is required")
        @Pattern(regexp = "(?i)[A-Z0-9]{2}\\d{1,4}", message = "flight number must be 2 letters or digits followed by 1 to 4 digits, e.g. LO123")
        String flightNumber,

        @NotBlank(message = "origin is required")
        @Pattern(regexp = "(?i)[A-Z]{3}", message = "origin must be a 3-letter IATA code, e.g. WAW")
        String origin,

        @NotBlank(message = "destination is required")
        @Pattern(regexp = "(?i)[A-Z]{3}", message = "destination must be a 3-letter IATA code, e.g. WAW")
        String destination,

        @FutureOrPresent(message = "boarding time must not be in the past")
        Instant boardingAt,

        @FutureOrPresent(message = "gate closing time must not be in the past")
        Instant gateClosesAt,

        @NotNull(message = "scheduled departure date is required")
        @FutureOrPresent(message = "scheduled departure date must not be in the past")
        LocalDate scheduledDepartureDate,

        @NotNull(message = "estimated departure time is required")
        @FutureOrPresent(message = "estimated departure time must not be in the past")
        Instant estimatedDepartureAt,

        @NotNull(message = "capacity is required")
        @Min(value = 1, message = "capacity must be at least 1")
        Short capacity,

        @Pattern(regexp = "(?i)[A-Z]\\d{1,3}", message = "gate must be a letter followed by 1 to 3 digits")
        String gate
) {
    @AssertTrue(message = "Origin and destination must differ")
    public boolean isOriginNotDestination(){
            if (origin == null || destination == null){
                    return true;
            }
            return !origin.equalsIgnoreCase(destination);
    }

    @AssertTrue(message = "gate_closes_at must be > boarding_at")
    public boolean isGateClosesAfterBoardingAt(){
            if(gateClosesAt == null || boardingAt == null){
                    return true;
            }
        return gateClosesAt.isAfter(boardingAt);
    }
}

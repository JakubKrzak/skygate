package com.bush.skygate.flight;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface FlightRepository extends JpaRepository<Flight, Long> {
    /** Finds a flight by its externally visible UUID. */
    Optional<Flight> findByPublicId(UUID publicId);

    /** Returns one page of flights with the given flight number (it may run on many dates). */
    Page<Flight> findByFlightNumber(String flightNumber, Pageable pageable);

    /** Checks whether a flight with this number, departure date and origin already exists. */
    boolean existsByFlightNumberAndScheduledDepartureDateAndOrigin(String flightNumber, LocalDate scheduledDepartureDate, String origin);

    Optional<Flight> findByFlightNumber(String flightNumber);
}

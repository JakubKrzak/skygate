package com.bush.skygate.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByFlightNumber(String flightNumber);

    Optional<Flight> findByPublicId(UUID publicId);

}

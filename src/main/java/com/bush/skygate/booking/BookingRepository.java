package com.bush.skygate.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    /**
     * Finds a booking by its public identifier.
     *
     * @param publicId the externally visible UUID of the booking
     * @return the booking, or empty if none matches
     */
    Optional<Booking> findByPublicId(UUID publicId);

    /**
     * Finds a booking by its human-readable reference (the 6-character code on the ticket).
     *
     * @param bookingReference the booking reference, normalized to upper case by the caller
     * @return the booking, or empty if none matches
     */
    Optional<Booking> findByBookingReference(String bookingReference);
}

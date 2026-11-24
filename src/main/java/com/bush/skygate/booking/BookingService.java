package com.bush.skygate.booking;

import com.bush.skygate.booking.dto.BookingResponse;
import com.bush.skygate.common.error.BusinessRuleException;
import com.bush.skygate.common.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    public BookingService(BookingRepository bookingRepository,
                          BookingMapper bookingMapper){
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
    }

    /**
     * Finds a booking by its public identifier.
     *
     * @param publicId the externally visible UUID of the booking (not the database id)
     * @return the booking as a DTO
     * @throws BusinessRuleException with {@link ErrorCode#BOOKING_NOT_FOUND} if no booking has this id
     */
    public BookingResponse getByPublicId(UUID publicId){
         Booking booking = bookingRepository.findByPublicId(publicId)
                 .orElseThrow(() -> new BusinessRuleException(ErrorCode.BOOKING_NOT_FOUND, "Booking not found: publicId=" + publicId));
         return bookingMapper.toResponse(booking);
    }
}

package com.bush.skygate.booking;


import com.bush.skygate.booking.dto.BookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface BookingMapper {
    /**
     * Converts a booking entity to its API representation.
     * <p>
     * Only fields present in {@link BookingResponse} are exposed; the database id is never leaked.
     *
     * @param booking the entity to convert
     * @return the response DTO
     */
    BookingResponse toResponse(Booking booking);
}

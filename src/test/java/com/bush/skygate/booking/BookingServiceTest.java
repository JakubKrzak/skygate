package com.bush.skygate.booking;

import com.bush.skygate.booking.dto.BookingResponse;
import com.bush.skygate.common.error.BusinessRuleException;
import com.bush.skygate.common.error.ErrorCode;
import com.bush.skygate.flight.FlightMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    private static final UUID PUBLIC_ID = UUID.fromString("fc73dac2-f386-4cef-8a9d-921f26bcb751");

    @Mock BookingRepository bookingRepository;
    @Mock BookingMapper bookingMapper;

    @InjectMocks BookingService bookingService;

    @Test
    public void getByPublicId_returnsBooking_whenBookingExists(){
        BookingResponse response = aBookingResponse();
        Booking entity = aBookingEntity();

        when(bookingRepository.findByPublicId(PUBLIC_ID)).thenReturn(Optional.of(entity));
        when(bookingMapper.toResponse(entity)).thenReturn(response);

        BookingResponse result = bookingService.getByPublicId(PUBLIC_ID);
        assertThat(result).isEqualTo(response);
    }
    @Test
    public void getByPublicId_throwBookingNotFoundError_whenBookingNotExists(){
        when(bookingRepository.findByPublicId(PUBLIC_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookingService.getByPublicId(PUBLIC_ID))
                .isInstanceOf(BusinessRuleException.class)
                .extracting("code")
                .isEqualTo(ErrorCode.BOOKING_NOT_FOUND);

        verifyNoInteractions(bookingMapper);
    }

    private  BookingResponse aBookingResponse(){
        return new BookingResponse(PUBLIC_ID, "GG2XKW", BookingStatus.CONFIRMED,
                "MOBILE_APP", "anna@gmail.com", "Anna Jankowska", "+48927290773", BookingLanguage.pl);
    }

    private Booking aBookingEntity(){
        return new Booking(
                "MOBILE_APP", "Anna Jankowska", "anna@gmail.com",
                "+48927290773", BookingLanguage.pl, "GG2XKW");
    }
}

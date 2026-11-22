package com.bush.skygate.flight;

import com.bush.skygate.common.error.BusinessRuleException;
import com.bush.skygate.common.error.ErrorCode;
import com.bush.skygate.flight.dto.FlightCreate;
import com.bush.skygate.flight.dto.FlightResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {
    private static final UUID PUBLIC_ID =
            UUID.fromString("3f2b8c1e-5a4d-4e7b-9c6a-1d2e3f4a5b6c");

    @Mock FlightRepository flightRepository;
    @Mock FlightMapper flightMapper;

    @InjectMocks FlightService flightService;

    @Test
    void getByPublicId_returnsFlight_whenFlightExists() {
        Flight entity = aFlightEntity();
        FlightResponse response = aFlightResponse();

        when(flightRepository.findByPublicId(PUBLIC_ID)).thenReturn(Optional.of(entity));
        when(flightMapper.toResponse(entity)).thenReturn(response);

        FlightResponse result = flightService.getByPublicId(PUBLIC_ID);
        assertThat(result).isEqualTo(response);
    }

    @Test
    void getByPublicId_throwsException_whenFlightNotFound(){
        when(flightRepository.findByPublicId(PUBLIC_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightService.getByPublicId(PUBLIC_ID))
                .isInstanceOf(BusinessRuleException.class)
                .extracting("code")
                .isEqualTo(ErrorCode.FLIGHT_NOT_FOUND);

        verifyNoInteractions(flightMapper);
    }

    @Test
    void create_savesAndReturnsResponse_whenFlightDoesNotExist(){

        FlightCreate request = aFlightCreate();
        Flight entity = aFlightEntity();
        FlightResponse expected = aFlightResponse();

        when(flightMapper.toEntity(request)).thenReturn(entity);
        when(flightRepository.existsByFlightNumberAndScheduledDepartureDateAndOrigin(
                any(), any(), any()
        )).thenReturn(false);
        when(flightRepository.save(entity)).thenReturn(entity);
        when(flightMapper.toResponse(entity)).thenReturn(expected);

        FlightResponse result = flightService.create(request);
        verify(flightRepository).save(entity);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void create_andThrowException_FlightExists(){
        FlightCreate request = aFlightCreate();
        Flight entity = aFlightEntity();

        when(flightMapper.toEntity(request)).thenReturn(entity);
        when(flightRepository.existsByFlightNumberAndScheduledDepartureDateAndOrigin(
                any(), any(), any()
        )).thenReturn(true);

        assertThatThrownBy(() -> flightService.create(request))
                .isInstanceOf(BusinessRuleException.class)
                .extracting("code")
                .isEqualTo(ErrorCode.FLIGHT_EXISTS);

        verify(flightRepository, never()).save(any());
    }


    private Instant createDepartureDate(){
        return Instant.parse("2026-12-10T09:00:00Z");
    }

    private LocalDate createDate(){
        return LocalDate.of(2026, 12, 10);
    }

    private FlightCreate aFlightCreate() {
        return new FlightCreate(
                "LO123", "WAW", "JFK", null, null, createDate(), createDepartureDate(), (short) 180, "A12");
    }

    private Flight aFlightEntity() {
        return new Flight(
                "LO123", "WAW", "JFK", (short) 180, createDate(), createDepartureDate(), "A12", null, null);
    }


    private FlightResponse aFlightResponse(){
        return new FlightResponse(
                PUBLIC_ID, "LO123", "WAW", "JFK", (short) 180, createDate(),
                createDepartureDate(), "A12", FlightStatus.SCHEDULED, null, null);
    }
}

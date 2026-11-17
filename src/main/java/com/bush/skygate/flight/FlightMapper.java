package com.bush.skygate.flight;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightMapper{
    FlightResponse toResponse(Flight flight);
}

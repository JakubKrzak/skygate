package com.bush.skygate.flight;
import com.bush.skygate.flight.dto.FlightCreate;
import com.bush.skygate.flight.dto.FlightResponse;
import com.bush.skygate.flight.dto.FlightSummaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FlightMapper{
    FlightResponse toResponse(Flight flight);
    FlightSummaryResponse toSummary(Flight flight);
    List<FlightSummaryResponse> toSummaries(List<Flight> flights);
    Flight toEntity(FlightCreate request);
}

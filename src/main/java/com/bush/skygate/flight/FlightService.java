package com.bush.skygate.flight;

import com.bush.skygate.common.error.BusinessRuleException;
import com.bush.skygate.common.error.ErrorCode;

import com.bush.skygate.flight.dto.FlightCreate;
import com.bush.skygate.flight.dto.FlightResponse;
import com.bush.skygate.flight.dto.FlightSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;


/**
 * Business logic for flights.
 * <p>
 * Read-only by default; methods that modify data must override this
 * with a plain {@code @Transactional}.
 */

@Service
@Transactional(readOnly = true)
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper){
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
    }

    /**
     * Finds a flight by its public identifier.
     *
     * @param publicId the externally visible UUID of the flight (not the database id)
     * @return the flight as a response DTO, never {@code null}
     * @throws BusinessRuleException with {@link ErrorCode#FLIGHT_NOT_FOUND} if no flight has this id
     */
    public FlightResponse getByPublicId(UUID publicId){
        Flight flight = flightRepository.findByPublicId(publicId)
                                .orElseThrow(() -> new BusinessRuleException(ErrorCode.FLIGHT_NOT_FOUND, "Flight not found: publicId=" + publicId));
        return flightMapper.toResponse(flight);
    }

    /**
    * Searches flights by flight number and returns one page of summaries.
     * <p>
    * The same flight number can run on many dates, so the result is a page,
    * not a single flight. An empty page is a valid result, not an error.
    *
    * @param flightNumber the flight number to match exactly (e.g. {@code LO123})
    * @param pageable     requested page number, page size and sort order
    * @return a page of flight summaries, empty if nothing matches
     * */
    public Page<FlightSummaryResponse> search(String flightNumber, Pageable pageable) {
        return flightRepository.findByFlightNumber(flightNumber, pageable)
                .map(flightMapper::toSummary);
    }

    /**
     * Creates a new flight.
     * <p>
     * A flight is identified by flight number, scheduled departure date and origin;
     * the same number may run on other dates or routes, so only that exact
     * combination is rejected as a duplicate.
     *
     * @param request validated data of the flight to create
     * @return the saved flight, including its generated public id
     * @throws BusinessRuleException with {@link ErrorCode#FLIGHT_EXISTS} if a flight
     *         with the same number, departure date and origin already exists
     */
    @Transactional
    public FlightResponse create(FlightCreate request){
        Flight flightMappedEntity = flightMapper.toEntity(request);
        assertFlightIsUnique(
                flightMappedEntity.getFlightNumber(),
                flightMappedEntity.getScheduledDepartureDate(),
                flightMappedEntity.getOrigin());

        Flight flight = flightRepository.save(flightMappedEntity);
        return flightMapper.toResponse(flight);

    }

    /**
     * Fails fast if a flight with the same number, departure date and origin exists.
     * <p>
     * This is a friendly pre-check only; the database unique constraint remains
     * the final guard against concurrent inserts.
     *
     * @throws BusinessRuleException with {@link ErrorCode#FLIGHT_EXISTS} on a duplicate
     */
    private void assertFlightIsUnique(String flightNumber, LocalDate scheduledDepartureDate, String origin){
        if (flightRepository.existsByFlightNumberAndScheduledDepartureDateAndOrigin(
                flightNumber,
                scheduledDepartureDate,
                origin)){
            throw new BusinessRuleException(
                    ErrorCode.FLIGHT_EXISTS,
                    "Flight %s from %s on %s already exists".formatted(
                            flightNumber,
                            origin,
                            scheduledDepartureDate));
        }
    }
}

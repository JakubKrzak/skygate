package com.bush.skygate.flight;

import com.bush.skygate.common.error.BusinessRuleException;
import com.bush.skygate.common.error.ErrorCode;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


}

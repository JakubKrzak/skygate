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

    public FlightService(FlightRepository flightRepository){
        this.flightRepository = flightRepository;
    }

    /**
     * Finds a flight by its public identifier.
     *
     * @param publicId the externally visible UUID of the flight (not the database id)
     * @return the matching flight, never {@code null}
     * @throws BusinessRuleException with {@link ErrorCode#FLIGHT_NOT_FOUND} if no flight has this id
     */
    public Flight getByPublicId(UUID publicId){
        return flightRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessRuleException(ErrorCode.FLIGHT_NOT_FOUND, "Flight not found: publicId=" + publicId));
    }


}

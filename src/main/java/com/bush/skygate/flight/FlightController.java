package com.bush.skygate.flight;

import com.bush.skygate.flight.dto.FlightCreate;
import com.bush.skygate.flight.dto.FlightResponse;
import com.bush.skygate.flight.dto.FlightSummaryResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    /**
     * {@code GET /api/v1/flights/{publicId}}: returns full details of one flight.
     *
     * @param publicId the externally visible UUID of the flight
     * @return {@code 200 OK} with the flight details
     *         ({@code 404} is produced by the global exception handler if not found)
     */
    @GetMapping("/{publicId}")
    public FlightResponse getByPublicId(
            @PathVariable UUID publicId){
        return flightService.getByPublicId(publicId);
    }

    /**
     * {@code GET /api/v1/flights}: searches flights with pagination.
     * <p>
     * Query parameters: {@code flightNumber} to filter, plus {@code page} (zero-based),
     * {@code size} and {@code sort}. Defaults to 20 results per page, sorted by
     * scheduled departure date.
     *
     * @param flightNumber flight number to filter by
     * @param pageable     pagination and sorting, resolved from query parameters
     * @return {@code 200 OK} with a page of flight summaries (empty if nothing matches)
     */
    @GetMapping
    public Page<FlightSummaryResponse> getByFlightNumber(
            @RequestParam(required = false) String flightNumber,
            @PageableDefault(size = 20, sort = "scheduledDepartureDate") Pageable pageable){
        return flightService.search(flightNumber, pageable);
    }

    /**
     * {@code POST /api/v1/flights}: creates a new flight.
     *
     * @param request flight data; validated before reaching the service
     * @return {@code 201 Created} with the new flight and a {@code Location} header
     *         pointing to it ({@code 400} on invalid input and {@code 409} on a
     *         duplicate are produced by the global exception handler)
     */
    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(
            @Valid @RequestBody FlightCreate request){

        FlightResponse created = flightService.create(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{publicId}")
                .buildAndExpand(created.publicId())
                .toUri();

        return ResponseEntity.created(location).body(created);


    }
}

package com.bush.skygate.flight;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    @GetMapping("/{publicId}")
    public FlightResponse getByPublicId(
            @PathVariable UUID publicId
    ){
        return flightService.getByPublicId(publicId);
    }


}

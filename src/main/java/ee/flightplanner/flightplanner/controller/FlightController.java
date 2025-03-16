package ee.flightplanner.flightplanner.controller;

import ee.flightplanner.flightplanner.dto.FlightDto;
import ee.flightplanner.flightplanner.dto.booking.FlightTableDto;
import ee.flightplanner.flightplanner.dto.searchcriteria.FlightTableSearchCriteria;
import ee.flightplanner.flightplanner.dto.PageResponse;
import ee.flightplanner.flightplanner.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/flights")
@Tag(name = "Flights", description = "APIs for managing flights")
public class FlightController {

    private final FlightService flightService;

    @Operation(
            summary = "Get flight by ID",
            description = "Fetches a flight by its unique ID."
    )
    @ApiResponse(responseCode = "200", description = "Flight retrieved successfully!")
    @ApiResponse(responseCode = "404", description = "Flight not found!")
    @ApiResponse(responseCode = "500", description = "Internal server error!")
    @GetMapping("/{flightId}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Integer flightId) {
        FlightDto flight = flightService.getFlightById(flightId);
        return ResponseEntity.ok(flight);
    }

    @Operation(
            summary = "Search for flights",
            description = "Fetches a paginated and filtered list of all the flights."
    )
    @ApiResponse(responseCode = "200", description = "Flights retrieved successfully!")
    @ApiResponse(responseCode = "500", description = "Internal server error!")
    @GetMapping("/table")
    public ResponseEntity<PageResponse<FlightTableDto>> searchFlights(@Valid FlightTableSearchCriteria criteria) {
        if (criteria == null) {
            criteria = new FlightTableSearchCriteria();
        }
        PageResponse<FlightTableDto> response = flightService.searchFlights(criteria);
        return ResponseEntity.ok(response);
    }
}

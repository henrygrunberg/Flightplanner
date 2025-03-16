package ee.flightplanner.flightplanner.controller;

import ee.flightplanner.flightplanner.dto.booking.FlightSeatLayoutDto;
import ee.flightplanner.flightplanner.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/{flightId}")
    @Operation(summary = "Get seat layout for a flight", description = "Returns the seating layout, including seat class and availability")
    @ApiResponse(responseCode = "200", description = "Seating layout, including seat class and availability retrieved successfully!")
    @ApiResponse(responseCode = "500", description = "Internal server error!")
    public ResponseEntity<FlightSeatLayoutDto> getFlightSeatLayout(@PathVariable Integer flightId) {
        return ResponseEntity.ok(seatService.getFlightSeatLayout(flightId));
    }
}

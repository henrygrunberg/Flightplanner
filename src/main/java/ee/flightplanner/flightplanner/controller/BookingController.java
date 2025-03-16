package ee.flightplanner.flightplanner.controller;

import ee.flightplanner.flightplanner.dto.booking.BookingRequestDto;
import ee.flightplanner.flightplanner.dto.booking.FinalizeBookingRequestDto;
import ee.flightplanner.flightplanner.dto.booking.SeatRecommendationResponseDto;
import ee.flightplanner.flightplanner.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@Tag(name = "Flight Booking", description = "APIs for handling flight booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Generate seat recommendations", description = "Returns recommended seats for all passengers")
    @ApiResponse(responseCode = "200", description = "Seat recommendations generated successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error!")
    @PostMapping("/recommend-seats")
    public ResponseEntity<SeatRecommendationResponseDto> recommendSeats(@RequestBody BookingRequestDto bookingRequest) {
        SeatRecommendationResponseDto recommendations = bookingService.generateSeatRecommendations(bookingRequest);
        return ResponseEntity.ok(recommendations);
    }

    @Operation(summary = "Cancel pending bookings", description = "Deletes all pending bookings for a flight")
    @ApiResponse(responseCode = "200", description = "Pending bookings deleted successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error!")
    @DeleteMapping("/cancel/{flightId}")
    public ResponseEntity<String> cancelPendingBookings(@PathVariable Integer flightId) {
        bookingService.cancelPendingBookings(flightId);
        return ResponseEntity.ok("Pending bookings deleted successfully.");
    }

    @PostMapping("/finalize")
    @Operation(summary = "Finalize the booking", description = "Stores the passenger booking details and assigns seats.")
    @ApiResponse(responseCode = "200", description = "Posting booking details and assigning seats was successful.")
    @ApiResponse(responseCode = "500", description = "Internal server error!")
    public ResponseEntity<String> finalizeBooking(@RequestBody FinalizeBookingRequestDto bookingRequest) {
        bookingService.finalizeBooking(bookingRequest);
        return ResponseEntity.ok("Booking finalized successfully!");
    }
}

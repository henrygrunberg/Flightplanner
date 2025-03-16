package ee.flightplanner.flightplanner.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Finalized passenger booking details")
public class PassengerBookingDto {

    @Schema(description = "Passenger's name", example = "Alice Johnson")
    private String passengerName;

    @Schema(description = "Nationality of the passenger", example = "Estonia")
    private String nationality;

    @Schema(description = "Passport number", example = "A12345678")
    private String passportNumber;

    @Schema(description = "Seat class", example = "Economy")
    private String seatClass;

    @Schema(description = "Seat number assigned", example = "12A")
    private String seatNumber;

    @Schema(description = "Booking status", example = "CONFIRMED")
    private String bookingStatus;
}

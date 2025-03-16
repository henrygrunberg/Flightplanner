package ee.flightplanner.flightplanner.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Booking request containing multiple passengers")
public class BookingRequestDto {

    @Schema(description = "Flight ID for the booking", example = "1001")
    private Integer flightId;

    @Schema(description = "List of passengers and their booking information")
    private List<InitializePassengerDto> passengers;
}

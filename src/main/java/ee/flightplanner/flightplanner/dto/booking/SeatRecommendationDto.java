package ee.flightplanner.flightplanner.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Recommended Seats for Passenger")
public class SeatRecommendationDto {

    @Schema(description = "Passenger's name", example = "Alice Johnson")
    private String passengerName;

    @Schema(description = "Recommended seat number", example = "12A")
    private String recommendedSeat;

    @Schema(description = "Seat class", example = "Economy")
    private String seatClass;

    @Schema(description = "Reason for recommendation", example = "Window seat as per preference")
    private String reason;
}

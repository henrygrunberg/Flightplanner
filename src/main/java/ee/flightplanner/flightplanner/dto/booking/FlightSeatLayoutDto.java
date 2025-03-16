package ee.flightplanner.flightplanner.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Seat layout and availability for a specific flight")
public class FlightSeatLayoutDto {

    @Schema(description = "Flight ID", example = "1001")
    private Integer flightId;

    @Schema(description = "List of seat rows")
    private List<SeatRowDetailsDto> seatRows;
}
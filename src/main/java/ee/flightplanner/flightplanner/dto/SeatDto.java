package ee.flightplanner.flightplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Data Transfer Object for Seat information")
public class SeatDto {
    @Schema(description = "Seat Id", example = "1")
    private Integer seatId;

    @Schema(description = "Flight Id", example = "1")
    private Integer flightId;

    @Schema(description = "Seat layout Id", example = "1")
    private Integer seatLayoutId;

    @Schema(description = "Is the seat available?", example = "true")
    private Boolean isAvailable;
}

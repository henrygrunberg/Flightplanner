package ee.flightplanner.flightplanner.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Detailed information about a seat")
public class SeatDetailsDto {

    @Schema(description = "Seat position (A, B, C, etc.)", example = "A")
    private String seatPosition;

    @Schema(description = "Seat class (Economy, Business, First Class)", example = "Economy")
    private String seatClass;

    @Schema(description = "Is this a window seat?", example = "true")
    private Boolean isWindow;

    @Schema(description = "Is this an aisle seat?", example = "false")
    private Boolean isAisle;

    @Schema(description = "Does this seat have extra legroom?", example = "false")
    private Boolean isExtraLegroom;

    @Schema(description = "Is this seat available?", example = "true")
    private Boolean isAvailable;
}
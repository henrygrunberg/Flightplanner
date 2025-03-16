package ee.flightplanner.flightplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Data Transfer Object for Seat Layout information")
public class SeatLayoutDto {
    @Schema(description = "Seat Layout Id", example = "1")
    private Integer seatLayoutId;

    @Schema(description = "Seat Row Id", example = "2")
    private Integer rowId;

    @Schema(description = "Seat Position in the row", example = "A")
    private String seatPosition;

    @Schema(description = "Seat Class (Economy, Business, First Class)", example = "Economy")
    private String seatClass;

    @Schema(description = "Is this a window seat?", example = "true")
    private Boolean isWindow;

    @Schema(description = "Is this an aisle seat?", example = "false")
    private Boolean isAisle;

    @Schema(description = "Does this seat have extra legroom?", example = "false")
    private Boolean isExtraLegroom;
}

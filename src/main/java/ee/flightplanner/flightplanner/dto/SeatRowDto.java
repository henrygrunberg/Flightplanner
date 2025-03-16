package ee.flightplanner.flightplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Data Transfer Object for Seat Row information")
public class SeatRowDto {
    @Schema(description = "Seat Row Id", example = "2")
    private Integer seatRowId;

    @Schema(description = "Airplane Id this row belongs to", example = "1")
    private Integer airplaneId;

    @Schema(description = "Row Number", example = "15")
    private Integer rowNumber;
}

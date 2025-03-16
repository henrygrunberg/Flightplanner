package ee.flightplanner.flightplanner.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Details of a seat row including its seats")
public class SeatRowDetailsDto {

    @Schema(description = "Row number in the airplane", example = "12")
    private Integer rowNumber;

    @Schema(description = "Airplane ID", example = "1")
    private Integer airplaneId;

    @Schema(description = "Seats in this row")
    private List<SeatDetailsDto> seats;
}
package ee.flightplanner.flightplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Data Transfer Object for Airplane information")
public class AirplaneDto {
    @Schema(description = "Airplane Id", example = "1")
    private Integer airplaneId;

    @Schema(description = "Airline Id", example = "1")
    private Integer airlineId;

    @Schema(description = "Airplane model", example = "Boeing 737")
    private String model;

    @Schema(description = "Tail number", example = "AF737-001")
    private String tailNumber;
}

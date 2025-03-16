package ee.flightplanner.flightplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Data Transfer Object for Airline information")
public class AirlineDto {
    @Schema(description = "Airline Id", example = "1")
    private Integer airlineId;

    @Schema(description = "Name of the airline", example = "Sky Airways")
    private String name;

    @Schema(description = "Customer support e-mail address", example = "support@skyairways.com")
    private String csMail;
}

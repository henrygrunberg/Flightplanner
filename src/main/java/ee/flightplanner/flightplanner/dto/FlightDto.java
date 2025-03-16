package ee.flightplanner.flightplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Data Transfer Object for Flight information")
public class FlightDto {
    @Schema(description = "Flight Id", example = "1")
    private Integer flightId;

    @Schema(description = "Airline Name", example = "Emirates")
    private String airlineName;

    @Schema(description = "Airplane Model", example = "Boeing 737")
    private String airplaneModel;

    @Schema(description = "Departure airport", example = "New York JFK")
    private String departure;

    @Schema(description = "Destination airport", example = "Los Angeles LAX")
    private String destination;

    @Schema(description = "Departure date", example = "2025-03-02 14:00:00")
    private LocalDateTime departureDate;

    @Schema(description = "Arrival date", example = "2025-03-02 14:00:00")
    private LocalDateTime arrivalDate;

    @Schema(description = "Starting price for 1 seat", example = "59.99")
    private Double price;
}

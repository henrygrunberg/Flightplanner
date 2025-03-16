package ee.flightplanner.flightplanner.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Passenger Seat Preference Information")
public class InitializePassengerDto {

    @Schema(description = "Passenger's name", example = "Alice Johnson")
    private String passengerName;

    @Schema(description = "Nationality of the passenger", example = "Estonia")
    private String nationality;

    @Schema(description = "Passport number", example = "A12345678")
    private String passportNumber;

    @Schema(description = "Preferred seat class", example = "Economy") // Economy business, first
    private String seatClass;

    @Schema(description = "Seat preference", example = "window") // window, aisle, legroom, exit, together
    private String seatPreference;
}

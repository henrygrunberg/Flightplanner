package ee.flightplanner.flightplanner.dto.booking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Response containing seat recommendations for all passengers")
public class SeatRecommendationResponseDto {

    @Schema(description = "Flight ID for the booking", example = "1001")
    private Integer flightId;

    @Schema(description = "List of seat recommendations for each passenger")
    private List<SeatRecommendationDto> recommendations;
}

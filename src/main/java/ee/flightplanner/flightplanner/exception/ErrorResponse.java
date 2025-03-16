package ee.flightplanner.flightplanner.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class ErrorResponse {
    private String message;
}

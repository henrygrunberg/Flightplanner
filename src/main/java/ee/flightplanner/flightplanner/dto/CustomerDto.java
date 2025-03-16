package ee.flightplanner.flightplanner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Data Transfer Object for Customer information")
public class CustomerDto {
    @Schema(description = "Customer Id", example = "1")
    private Integer customerId;

    @Schema(description = "Username", example = "John Black")
    private String username;

    @Schema(description = "Email Address", example = "john@example.com")
    private String email;

    @Schema(description = "Balance available for booking flights", example = "500.00")
    private Double balance;
}

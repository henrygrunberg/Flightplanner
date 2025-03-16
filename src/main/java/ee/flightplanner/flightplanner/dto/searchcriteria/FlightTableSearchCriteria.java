package ee.flightplanner.flightplanner.dto.searchcriteria;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FlightTableSearchCriteria {

    private String destination;
    private String departure;

    private LocalDate DepartureStartDate;
    private LocalDate DepartureEndDate;

    @PositiveOrZero
    private Double minPrice;
    @PositiveOrZero
    private Double maxPrice;

    private String airlineName;

    // Pagination and sorting
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDirection;
}

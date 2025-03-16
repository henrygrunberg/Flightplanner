package ee.flightplanner.flightplanner.service;

import ee.flightplanner.flightplanner.dto.FlightDto;
import ee.flightplanner.flightplanner.dto.booking.FlightTableDto;
import ee.flightplanner.flightplanner.dto.PageResponse;
import ee.flightplanner.flightplanner.dto.searchcriteria.FlightTableSearchCriteria;
import ee.flightplanner.flightplanner.entity.FlightEntity;
import ee.flightplanner.flightplanner.mapping.FlightMapping;
import ee.flightplanner.flightplanner.repository.FlightRepository;
import ee.flightplanner.flightplanner.specifications.FlightSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapping flightMapping;
    private static final Logger log = LoggerFactory.getLogger(FlightService.class);


    public FlightService(FlightRepository flightRepository, FlightMapping flightMapping) {
        this.flightRepository = flightRepository;
        this.flightMapping = flightMapping;
    }

    public FlightDto getFlightById(Integer flightId) {
        FlightEntity flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Flight not found"));
        return flightMapping.flightToDto(flight);
    }

    public PageResponse<FlightTableDto> searchFlights(FlightTableSearchCriteria criteria) {
        log.info("Searching flights with criteria: {}", criteria);

        // Default pagination values
        int page = (criteria.getPage() != null) ? criteria.getPage() : 0;
        int size = (criteria.getSize() != null) ? criteria.getSize() : 10;
        String sortBy = (criteria.getSortBy() != null) ? criteria.getSortBy() : "departureDate";

        if ("airlineName".equalsIgnoreCase(sortBy)) {
            sortBy = "airline.name";
        }

        Sort.Direction direction = (criteria.getSortDirection() == null || "desc".equalsIgnoreCase(criteria.getSortDirection()))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Specification<FlightEntity> spec = Specification.where(
                        FlightSpecifications.destinationLike(criteria.getDestination()))
                .and(FlightSpecifications.departureLike(criteria.getDeparture()))
                .and(FlightSpecifications.departureDateBetween(criteria.getDepartureStartDate(), criteria.getDepartureEndDate()))
                .and(FlightSpecifications.priceBetween(criteria.getMinPrice(), criteria.getMaxPrice()))
                .and(FlightSpecifications.airlineNameLike(criteria.getAirlineName()));

        Page<FlightEntity> flightEntities = flightRepository.findAll(spec, pageable);
        Page<FlightTableDto> flightDtos = flightEntities.map(flightMapping::flightTableToDtoTable);
        log.info("Found {} flights matching the criteria.", flightDtos.getTotalElements());
        return new PageResponse<>(flightDtos);
    }
}

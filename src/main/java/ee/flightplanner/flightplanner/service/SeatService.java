package ee.flightplanner.flightplanner.service;

import ee.flightplanner.flightplanner.dto.booking.FlightSeatLayoutDto;
import ee.flightplanner.flightplanner.dto.booking.SeatDetailsDto;
import ee.flightplanner.flightplanner.dto.booking.SeatRowDetailsDto;
import ee.flightplanner.flightplanner.entity.SeatEntity;
import ee.flightplanner.flightplanner.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private static final Logger log = LoggerFactory.getLogger(SeatService.class);

    public FlightSeatLayoutDto getFlightSeatLayout(Integer flightId) {
        log.info("Fetching seat layout for flight ID: {}", flightId);

        // Fetch all seats for the given flight
        List<SeatEntity> seats = seatRepository.findByFlight_FlightId(flightId);
        if (seats.isEmpty()) {
            log.warn("No seats found for flight ID: {}", flightId);
            return new FlightSeatLayoutDto(flightId, List.of());
        }

        // Group seats by row
        Map<Integer, List<SeatEntity>> seatsByRow = seats.stream()
                .collect(Collectors.groupingBy(seat -> seat.getSeatLayout().getSeatRow().getRowNumber()));

        List<SeatRowDetailsDto> seatRows = seatsByRow.entrySet().stream()
                .map(entry -> {
                    Integer rowNumber = entry.getKey();
                    List<SeatEntity> rowSeats = entry.getValue();

                    // Extract airplane ID (same for all seats in row)
                    Integer airplaneId = rowSeats.get(0).getSeatLayout().getSeatRow().getAirplane().getAirplaneId();

                    // Map seats to DTO format
                    List<SeatDetailsDto> seatDetails = rowSeats.stream()
                            .map(seat -> new SeatDetailsDto(
                                    seat.getSeatPosition(),
                                    seat.getSeatClass(),
                                    seat.isWindow(),
                                    seat.isAisle(),
                                    seat.isExtraLegroom(),
                                    seat.getIsAvailable()
                            )).toList();

                    return new SeatRowDetailsDto(rowNumber, airplaneId, seatDetails);
                })
                .sorted((row1, row2) -> Integer.compare(row1.getRowNumber(), row2.getRowNumber())).toList();

        log.info("Successfully retrieved seat layout for flight ID: {} with {} rows", flightId, seatRows.size());
        return new FlightSeatLayoutDto(flightId, seatRows);
    }
}

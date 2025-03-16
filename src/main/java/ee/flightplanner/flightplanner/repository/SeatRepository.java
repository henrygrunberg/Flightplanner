package ee.flightplanner.flightplanner.repository;

import ee.flightplanner.flightplanner.entity.SeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<SeatEntity, Integer>, JpaSpecificationExecutor<SeatEntity> {

    List<SeatEntity> findByFlight_FlightId(Integer flightId);

    @Query("SELECT s FROM SeatEntity s WHERE s.flight.flightId = :flightId AND s.isAvailable = true ORDER BY s.seatLayout.seatRow.rowNumber")
    List<SeatEntity> findAvailableSeatsByFlight(Integer flightId);

    @Query("SELECT s FROM SeatEntity s WHERE s.flight.flightId = :flightId AND CONCAT(s.seatLayout.seatRow.rowNumber, s.seatLayout.seatPosition) = :seatNumber")
    Optional<SeatEntity> findByFlightIdAndSeatNumber(Integer flightId, String seatNumber);

    @Query("SELECT MAX(s.seatLayout.seatRow.rowNumber) FROM SeatEntity s WHERE s.flight.flightId = :flightId")
    int findMaxRowNumberByFlight(Integer flightId);

    List<SeatEntity> findSeatsByFlight_FlightId(Integer flightId);


}

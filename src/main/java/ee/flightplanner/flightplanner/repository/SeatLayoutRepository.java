package ee.flightplanner.flightplanner.repository;

import ee.flightplanner.flightplanner.entity.SeatLayoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatLayoutRepository extends JpaRepository<SeatLayoutEntity, Integer>, JpaSpecificationExecutor<SeatLayoutEntity> {

    @Query("SELECT sl FROM SeatLayoutEntity sl " +
            "JOIN sl.seatRow sr " +
            "JOIN sr.airplane a " +
            "JOIN FlightEntity f ON f.airplane.airplaneId = a.airplaneId " +
            "WHERE f.flightId = :flightId")
    List<SeatLayoutEntity> findSeatLayoutByFlight(Integer flightId);
}

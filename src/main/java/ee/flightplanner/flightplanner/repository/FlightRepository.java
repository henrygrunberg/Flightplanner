package ee.flightplanner.flightplanner.repository;

import ee.flightplanner.flightplanner.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Integer>, JpaSpecificationExecutor<FlightEntity> {
}

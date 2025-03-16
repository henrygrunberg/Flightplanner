package ee.flightplanner.flightplanner.repository;

import ee.flightplanner.flightplanner.entity.SeatRowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRowRepository extends JpaRepository<SeatRowEntity, Integer>, JpaSpecificationExecutor<SeatRowEntity> {
}

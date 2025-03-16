package ee.flightplanner.flightplanner.repository;

import ee.flightplanner.flightplanner.entity.AirplaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplaneRepository extends JpaRepository<AirplaneEntity, Integer>, JpaSpecificationExecutor<AirplaneEntity> {
}

package ee.flightplanner.flightplanner.repository;

import ee.flightplanner.flightplanner.entity.AirlineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<AirlineEntity, Integer>, JpaSpecificationExecutor<AirlineEntity> {
}

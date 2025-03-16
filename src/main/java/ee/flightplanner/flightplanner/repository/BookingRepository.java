package ee.flightplanner.flightplanner.repository;

import ee.flightplanner.flightplanner.entity.BookingEntity;
import ee.flightplanner.flightplanner.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer>, JpaSpecificationExecutor<BookingEntity> {

    @Query("SELECT b FROM BookingEntity b WHERE b.flight.flightId = :flightId AND b.bookingStatus = :bookingStatus")
    List<BookingEntity> findByFlightIdAndBookingStatus(Integer flightId, BookingStatus bookingStatus);

    @Query("SELECT b FROM BookingEntity b WHERE b.bookingStatus = 'PENDING' AND b.createdAt <= :expirationTime")
    List<BookingEntity> findExpiredBookings(LocalDateTime expirationTime);
}

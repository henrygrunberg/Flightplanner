package ee.flightplanner.flightplanner.specifications;

import ee.flightplanner.flightplanner.entity.FlightEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class FlightSpecifications {

    private static final String DEPARTURE_DATE = "departureDate";
    private static final String DESTINATION = "destination";
    private static final String DEPARTURE = "departure";
    private static final String PRICE = "price";

    private FlightSpecifications() {}

    public static Specification<FlightEntity> airlineNameLike(String airlineName) {
        return (root, query, cb) -> {
            if (airlineName == null || airlineName.isEmpty()) return cb.conjunction();

            Join<Object, Object> airlineJoin = root.join("airline");
            return cb.like(cb.lower(airlineJoin.get("name")), "%" + airlineName.toLowerCase() + "%");
        };
    }

    public static Specification<FlightEntity> destinationLike(String destination) {
        return (root, query, cb) -> {
            if (destination == null || destination.isEmpty()) return null;
            return cb.like(cb.lower(root.get(DESTINATION)), "%" + destination.toLowerCase() + "%");
        };
    }

    public static Specification<FlightEntity> departureLike(String departure) {
        return (root, query, cb) -> {
            if (departure == null || departure.isEmpty()) return null;
            return cb.like(cb.lower(root.get(DEPARTURE)), "%" + departure.toLowerCase() + "%");
        };
    }

    public static Specification<FlightEntity> departureDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (start != null && end != null) {
                return cb.between(root.get(DEPARTURE_DATE), start.atStartOfDay(), end.atTime(23, 59, 59));
            } else if (start != null) {
                return cb.greaterThanOrEqualTo(root.get(DEPARTURE_DATE), start.atStartOfDay());
            } else {
                return cb.lessThanOrEqualTo(root.get(DEPARTURE_DATE), end.atTime(23, 59, 59));
            }
        };
    }

    public static Specification<FlightEntity> priceBetween(Double minPrice, Double maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice != null && maxPrice != null) {
                return cb.between(root.get(PRICE), minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(root.get(PRICE), minPrice);
            } else {
                return cb.lessThanOrEqualTo(root.get(PRICE), maxPrice);
            }
        };
    }
}

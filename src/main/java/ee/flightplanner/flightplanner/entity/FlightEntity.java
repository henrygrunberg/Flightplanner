package ee.flightplanner.flightplanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flight")
public class FlightEntity {
    @Id
    @GeneratedValue
    private Integer flightId;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private AirlineEntity airline;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private AirplaneEntity airplane;

    private String departure;
    private String destination;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Double price;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE, orphanRemoval = false)
    private List<BookingEntity> bookings;
}

package ee.flightplanner.flightplanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airplane")
public class AirplaneEntity {
    @Id
    @GeneratedValue
    private Integer airplaneId;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private AirlineEntity airline;

    @Column(length = 50)
    private String model;

    @Column(length = 50, unique = true)
    private String tailNumber;
}
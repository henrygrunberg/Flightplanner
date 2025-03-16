package ee.flightplanner.flightplanner.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "airline")
public class AirlineEntity {
    @Id
    @GeneratedValue
    private Integer airlineId;

    private String name;
    @Column(length = 100)
    private String csMail;
}

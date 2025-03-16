package ee.flightplanner.flightplanner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat_row")
public class SeatRowEntity {
    
    @Id
    @GeneratedValue
    private Integer seatRowId;

    @ManyToOne
    @JoinColumn(name = "airplane_id", nullable = false)
    private AirplaneEntity airplane;

    @Column(nullable = false)
    private Integer rowNumber;

    private Integer seatCount;

    @OneToMany(mappedBy = "seatRow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatLayoutEntity> seatLayouts;
}

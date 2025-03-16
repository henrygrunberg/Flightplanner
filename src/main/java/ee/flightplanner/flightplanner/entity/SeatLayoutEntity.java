package ee.flightplanner.flightplanner.entity;

import ee.flightplanner.flightplanner.enums.SeatClass;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat_layout")
public class SeatLayoutEntity {
    
    @Id
    @GeneratedValue
    private Integer seatLayoutId;

    @ManyToOne
    @JoinColumn(name = "row_id", nullable = false)
    private SeatRowEntity seatRow;

    @Column(length = 2)
    private String seatPosition;

    @Column(name = "seat_class", length = 50)
    @Enumerated(EnumType.STRING)
    private SeatClass seatClass;

    private Boolean isWindow;
    private Boolean isAisle;
    private Boolean isExtraLegroom;
}

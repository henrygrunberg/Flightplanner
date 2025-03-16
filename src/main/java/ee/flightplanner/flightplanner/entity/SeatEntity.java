package ee.flightplanner.flightplanner.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat")
public class SeatEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer seatId;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private FlightEntity flight;

    @ManyToOne
    @JoinColumn(name = "seat_layout_id", nullable = false)
    private SeatLayoutEntity seatLayout;

    @Column(nullable = false)
    private Boolean isAvailable;

    public String getSeatClass() {
        return seatLayout.getSeatClass().toString();
    }

    public boolean isWindow() {
        return seatLayout.getIsWindow();
    }

    public boolean isAisle() {
        return seatLayout.getIsAisle();
    }

    public boolean isExtraLegroom() {
        return seatLayout.getIsExtraLegroom();
    }

    public Integer getRowNumber() {
        return seatLayout.getSeatRow().getRowNumber();
    }

    public String getSeatPosition() {
        return seatLayout.getSeatPosition();
    }
}

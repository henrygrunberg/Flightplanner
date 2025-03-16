import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SeatInsertGenerator {
    public static void main(String[] args) {
        int flightId = 21;
        int lastSeatLayoutId = 992;
        Random random = new Random();
        String fileName = "seat-data-flight-21.xml";

        // flight_id <-> airplane_id
        // 1-1, 2-2, 3-3, 4-4, 11-2, 12-1, 13-3, 14-4, 15-5, 16-5, 17-1, 18-3, 19-3, 20-5, 21-5, 22-2

        // airplane_id <-> seat_layout_ids
        // 1 -> 1-182, 2 -> 183-356, 3 -> 357-542, 4 -> 543-714, 5 -> 715-992

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int seatLayoutId = 715; seatLayoutId <= lastSeatLayoutId; seatLayoutId++) {
                boolean isAvailable = random.nextDouble() > 0.7; // x% chance of being booked (false)

                writer.write("<insert tableName=\"seat\">"
                        + "<column name=\"flight_id\" value=\"" + flightId + "\"/>"
                        + "<column name=\"seat_layout_id\" value=\"" + seatLayoutId + "\"/>"
                        + "<column name=\"is_available\" valueBoolean=\"" + isAvailable + "\"/>"
                        + "</insert>\n");
            }

            System.out.println("Seat data successfully written to " + fileName);

        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}

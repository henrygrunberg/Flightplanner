package ee.flightplanner.flightplanner.service;

import ee.flightplanner.flightplanner.dto.booking.*;
import ee.flightplanner.flightplanner.entity.BookingEntity;
import ee.flightplanner.flightplanner.entity.SeatEntity;
import ee.flightplanner.flightplanner.enums.BookingStatus;
import ee.flightplanner.flightplanner.repository.BookingRepository;
import ee.flightplanner.flightplanner.repository.FlightRepository;
import ee.flightplanner.flightplanner.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    public SeatRecommendationResponseDto generateSeatRecommendations(BookingRequestDto bookingRequest) {
        Integer flightId = bookingRequest.getFlightId();
        List<InitializePassengerDto> passengers = bookingRequest.getPassengers();

        log.info("Generating seat recommendations for flight ID: {} with {} passengers", flightId, passengers.size());

        List<SeatEntity> availableSeats = seatRepository.findAvailableSeatsByFlight(flightId);
        if (availableSeats.isEmpty()) {
            log.warn("No available seats found for flight ID: {}", flightId);
            return new SeatRecommendationResponseDto(flightId, Collections.emptyList());
        }

        int maxRowNumber = seatRepository.findMaxRowNumberByFlight(flightId);
        log.info("Max row number for airplane: {}", maxRowNumber);

        Set<String> assignedSeats = new HashSet<>();
        List<SeatRecommendationDto> recommendations = new ArrayList<>();
        List<BookingEntity> pendingBookings = new ArrayList<>();

        List<InitializePassengerDto> togetherPassengers = passengers.stream()
                .filter(p -> p.getSeatPreference().equalsIgnoreCase("together"))
                .toList();

        List<InitializePassengerDto> exitPassengers = passengers.stream()
                .filter(p -> p.getSeatPreference().equalsIgnoreCase("exit"))
                .toList();

        List<InitializePassengerDto> individualPassengers = passengers.stream()
                .filter(p -> !p.getSeatPreference().equalsIgnoreCase("together") && !p.getSeatPreference().equalsIgnoreCase("exit"))
                .toList();

        log.info("Grouped {} passengers wanting to sit together.", togetherPassengers.size());
        log.info("Grouped {} passengers wanting exit row seats.", exitPassengers.size());

        if (!togetherPassengers.isEmpty()) {
            List<SeatEntity> bestGroupSeats = findBestAdjacentSeats(availableSeats, assignedSeats, togetherPassengers);
            for (int i = 0; i < togetherPassengers.size(); i++) {
                InitializePassengerDto passenger = togetherPassengers.get(i);
                SeatEntity seat = (i < bestGroupSeats.size()) ? bestGroupSeats.get(i) : null;
                assignSeat(passenger, seat, availableSeats, assignedSeats, recommendations, pendingBookings, flightId);
            }
        }

        for (InitializePassengerDto passenger : exitPassengers) {
            String seatClass = passenger.getSeatClass();
            SeatEntity seat = findBestExitRowSeat(availableSeats, assignedSeats, seatClass, maxRowNumber);
            assignSeat(passenger, seat, availableSeats, assignedSeats, recommendations, pendingBookings, flightId);
        }

        for (InitializePassengerDto passenger : individualPassengers) {
            SeatEntity seat = findBestSeat(availableSeats, assignedSeats, passenger, maxRowNumber);
            assignSeat(passenger, seat, availableSeats, assignedSeats, recommendations, pendingBookings, flightId);
        }

        bookingRepository.saveAll(pendingBookings);
        log.info("Saved {} pending bookings for flight ID: {}", pendingBookings.size(), flightId);

        return new SeatRecommendationResponseDto(flightId, recommendations);
    }

    private List<SeatEntity> findBestAdjacentSeats(List<SeatEntity> availableSeats,
            Set<String> assignedSeats, List<InitializePassengerDto> togetherPassengers) {

        int groupSize = togetherPassengers.size();
        String requiredClass = togetherPassengers.get(0).getSeatClass();

        // Collect all possible contiguous seat arrangements
        List<List<SeatEntity>> allPossibleGroups = findAllPossibleSeatCombinations(availableSeats, assignedSeats, groupSize, requiredClass);

        // If a full match is not found, try splitting into smaller groups
        if (allPossibleGroups.isEmpty()) {
            return fallbackToNearestSegments(availableSeats, assignedSeats, groupSize, requiredClass);
        }

        return selectBestSeatCombination(allPossibleGroups);
    }

    private List<List<SeatEntity>> findAllPossibleSeatCombinations(List<SeatEntity> availableSeats, Set<String> assignedSeats,
                                                                   int groupSize, String seatClass) {
        List<List<SeatEntity>> possibleCombinations = new ArrayList<>();
        Map<Integer, List<SeatEntity>> seatsByRow = availableSeats.stream()
                .filter(seat -> seat.getSeatLayout().getSeatClass().toString().equalsIgnoreCase(seatClass))
                .collect(Collectors.groupingBy(seat -> seat.getSeatLayout().getSeatRow().getRowNumber()));

        for (Map.Entry<Integer, List<SeatEntity>> rowEntry : seatsByRow.entrySet()) {
            List<SeatEntity> rowSeats = rowEntry.getValue();
            rowSeats.sort(Comparator.comparing(SeatEntity::getSeatPosition));

            List<SeatEntity> contiguousSeats = new ArrayList<>();
            for (SeatEntity seat : rowSeats) {
                if (!assignedSeats.contains(seat.getSeatLayout().getSeatRow().getRowNumber() + seat.getSeatLayout().getSeatPosition())) {
                    contiguousSeats.add(seat);
                    if (contiguousSeats.size() == groupSize) {
                        possibleCombinations.add(new ArrayList<>(contiguousSeats));
                        break;
                    }
                } else {
                    contiguousSeats.clear();
                }
            }
        }

        return possibleCombinations;
    }

    private List<SeatEntity> fallbackToNearestSegments(List<SeatEntity> availableSeats, Set<String> assignedSeats, int groupSize, String seatClass) {
        List<List<SeatEntity>> seatGroups = new ArrayList<>();
        List<SeatEntity> currentGroup = new ArrayList<>();

        for (SeatEntity seat : availableSeats) {
            if (seat.getSeatLayout().getSeatClass().toString().equalsIgnoreCase(seatClass) &&
                    !assignedSeats.contains(seat.getSeatLayout().getSeatRow().getRowNumber() + seat.getSeatLayout().getSeatPosition())) {
                currentGroup.add(seat);
            } else {
                if (!currentGroup.isEmpty()) seatGroups.add(new ArrayList<>(currentGroup));
                currentGroup.clear();
            }
        }

        if (!currentGroup.isEmpty()) seatGroups.add(currentGroup);

        // Sort by largest available segment
        seatGroups.sort((g1, g2) -> Integer.compare(g2.size(), g1.size()));

        List<SeatEntity> bestSegment = new ArrayList<>();
        int assignedCount = 0;

        for (List<SeatEntity> segment : seatGroups) {
            for (SeatEntity seat : segment) {
                bestSegment.add(seat);
                assignedSeats.add(seat.getSeatLayout().getSeatRow().getRowNumber() + seat.getSeatLayout().getSeatPosition());
                assignedCount++;
                if (assignedCount == groupSize) return bestSegment;
            }
        }

        return bestSegment;
    }


    private List<SeatEntity> selectBestSeatCombination(List<List<SeatEntity>> seatCombinations) {
        return seatCombinations.stream()
                .sorted(Comparator.comparingInt(this::scoreSeatCombination).reversed())
                .findFirst()
                .orElse(Collections.emptyList());
    }

    private int scoreSeatCombination(List<SeatEntity> seatGroup) {
        int score = seatGroup.size() * 10;
        int minRow = seatGroup.stream().mapToInt(s -> s.getSeatLayout().getSeatRow().getRowNumber()).min().orElse(0);
        int maxRow = seatGroup.stream().mapToInt(s -> s.getSeatLayout().getSeatRow().getRowNumber()).max().orElse(0);
        score -= (maxRow - minRow) * 5;
        return score;
    }

    private void assignSeat(InitializePassengerDto passenger, SeatEntity seat, List<SeatEntity> availableSeats,
                            Set<String> assignedSeats, List<SeatRecommendationDto> recommendations, List<BookingEntity> pendingBookings,
                            Integer flightId) {
        if (seat != null) {
            availableSeats.remove(seat);
            String seatNumber = seat.getRowNumber() + seat.getSeatPosition();
            assignedSeats.add(seatNumber);

            recommendations.add(new SeatRecommendationDto(passenger.getPassengerName(), seatNumber, seat.getSeatClass(),
                    generateRecommendationReason(passenger, seat)));

            BookingEntity booking = new BookingEntity();
            booking.setFlight(flightRepository.findById(flightId).orElseThrow(() -> new RuntimeException("Flight not found")));
            booking.setPassengerName(passenger.getPassengerName());
            booking.setNationality(passenger.getNationality());
            booking.setPassportNumber(passenger.getPassportNumber());
            booking.setSeatClass(seat.getSeatClass());
            booking.setSeatNumber(seatNumber);
            booking.setBookingStatus(BookingStatus.PENDING);
            booking.setCreatedAt(LocalDateTime.now());
            pendingBookings.add(booking);
        } else {
            recommendations.add(new SeatRecommendationDto(passenger.getPassengerName(), "N/A", passenger.getSeatClass(),
                    "No available seat matching preference"));
        }
    }

    private String generateRecommendationReason(InitializePassengerDto passenger, SeatEntity seat) {
        if (passenger.getSeatPreference().equalsIgnoreCase("window") && seat.isWindow()) return "Preferred window seat.";
        if (passenger.getSeatPreference().equalsIgnoreCase("aisle") && seat.isAisle()) return "Preferred aisle seat.";
        if (passenger.getSeatPreference().equalsIgnoreCase("legroom") && seat.isExtraLegroom()) return "Extra legroom seat.";
        if (passenger.getSeatPreference().equalsIgnoreCase("exit")) return "Exit row seat.";
        return "Assigned based on availability.";
    }


    private SeatEntity findBestSeat(List<SeatEntity> availableSeats, Set<String> assignedSeats, InitializePassengerDto passenger, int maxRowNumber) {
        return availableSeats.stream()
                .filter(seat -> seat.getSeatClass().equalsIgnoreCase(passenger.getSeatClass()))
                .filter(seat -> !assignedSeats.contains(seat.getRowNumber() + seat.getSeatPosition()))
                .sorted(Comparator.comparingInt(seat -> calculateSeatScore(seat, passenger, maxRowNumber)))
                .findFirst()
                .orElse(null);
    }

    private int calculateSeatScore(SeatEntity seat, InitializePassengerDto passenger, int maxRowNumber) {
        int score = 0;
        if (passenger.getSeatPreference().equalsIgnoreCase("window") && seat.isWindow()) score += 5;
        if (passenger.getSeatPreference().equalsIgnoreCase("aisle") && seat.isAisle()) score += 4;
        if (passenger.getSeatPreference().equalsIgnoreCase("legroom") && seat.isExtraLegroom()) score += 3;
        if (passenger.getSeatPreference().equalsIgnoreCase("exit")) {
            int exitZone = maxRowNumber / 5; // First 5 rows or last 5 rows dynamically
            if (seat.getRowNumber() <= exitZone || seat.getRowNumber() >= (maxRowNumber - exitZone + 1)) {
                score += 2;
            }
        }
        return -score;
    }

    private SeatEntity findBestExitRowSeat(List<SeatEntity> availableSeats, Set<String> assignedSeats, String seatClass, int maxRowNumber) {
        // Look for seats in the first 5 rows with matching seat class
        for (int row = 1; row <= 5; row++) {
            int finalRow = row;
            Optional<SeatEntity> seat = availableSeats.stream()
                    .filter(s -> s.getRowNumber() == finalRow)
                    .filter(s -> s.getSeatClass().equalsIgnoreCase(seatClass))
                    .filter(s -> !assignedSeats.contains(s.getRowNumber() + s.getSeatPosition()))
                    .findFirst();
            if (seat.isPresent()) return seat.get();
        }

        // Look for seats in the last 5 rows with matching seat class
        for (int row = maxRowNumber; row >= maxRowNumber - 4; row--) {
            int finalRow = row;
            Optional<SeatEntity> seat = availableSeats.stream()
                    .filter(s -> s.getRowNumber() == finalRow)
                    .filter(s -> s.getSeatClass().equalsIgnoreCase(seatClass))
                    .filter(s -> !assignedSeats.contains(s.getRowNumber() + s.getSeatPosition()))
                    .findFirst();
            if (seat.isPresent()) return seat.get();
        }

        // If no exit row available, find the closest row instead
        return availableSeats.stream()
                .filter(s -> s.getSeatClass().equalsIgnoreCase(seatClass))
                .filter(s -> !assignedSeats.contains(s.getRowNumber() + s.getSeatPosition()))
                .sorted(Comparator.comparingInt(SeatEntity::getRowNumber))
                .findFirst()
                .orElse(null);
    }


    public void cancelPendingBookings(Integer flightId) {
        log.info("Cancelling all pending bookings for flight ID: {}", flightId);
        List<BookingEntity> pendingBookings = bookingRepository.findByFlightIdAndBookingStatus(flightId, BookingStatus.PENDING);
        bookingRepository.deleteAll(pendingBookings);
        log.info("Deleted {} pending bookings for flight ID: {}", pendingBookings.size(), flightId);
    }


    @Scheduled(cron = "0 */1 * * * *")
    @Transactional
    public void removeExpiredPendingBookings() {
        // Runs every minute and deletes pending bookings older than 20 minutes.
        log.info("Running scheduled cleanup for expired pending bookings...");

        LocalDateTime twentyMinutesAgo = LocalDateTime.now().minus(20, ChronoUnit.MINUTES);
        List<BookingEntity> expiredBookings = bookingRepository.findExpiredBookings(twentyMinutesAgo);

        if (!expiredBookings.isEmpty()) {
            bookingRepository.deleteAll(expiredBookings);
            log.info("Deleted {} expired pending bookings.", expiredBookings.size());
        } else {
            log.info("No expired pending bookings found.");
        }
    }


    public void finalizeBooking(FinalizeBookingRequestDto finalizeRequest) {
        for (PassengerBookingDto passengerDto : finalizeRequest.getPassengers()) {
            BookingEntity booking = bookingRepository.findByFlightIdAndBookingStatus(finalizeRequest.getFlightId(), BookingStatus.PENDING)
                    .stream()
                    .filter(b -> b.getPassportNumber().equals(passengerDto.getPassportNumber()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Pending booking not found for passenger: " + passengerDto.getPassengerName()));

            // Update booking to CONFIRMED
            booking.setSeatClass(passengerDto.getSeatClass());
            booking.setSeatNumber(passengerDto.getSeatNumber());
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
            log.info("Finalized booking for passenger {} with seat {}", passengerDto.getPassengerName(), passengerDto.getSeatNumber());

            // Mark seat as unavailable
            SeatEntity seat = seatRepository.findByFlightIdAndSeatNumber(
                    finalizeRequest.getFlightId(),
                    passengerDto.getSeatNumber()
            ).orElseThrow(() -> new RuntimeException("Seat not found: " + passengerDto.getSeatNumber()));

            seat.setIsAvailable(false);
            seatRepository.save(seat);
            log.info("Marked seat {} as unavailable", passengerDto.getSeatNumber());
        }
    }
}
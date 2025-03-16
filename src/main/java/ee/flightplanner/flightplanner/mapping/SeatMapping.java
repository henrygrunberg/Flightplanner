package ee.flightplanner.flightplanner.mapping;

import ee.flightplanner.flightplanner.dto.SeatDto;
import ee.flightplanner.flightplanner.entity.SeatEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeatMapping {
    SeatDto seatToDto(SeatEntity seat);
    SeatEntity seatToEntity(SeatDto seatDto);
    List<SeatDto> seatListToDtoList(List<SeatEntity> seats);
    List<SeatEntity> seatListToEntityList(List<SeatDto> seatDtos);
}

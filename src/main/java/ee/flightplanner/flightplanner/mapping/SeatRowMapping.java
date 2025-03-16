package ee.flightplanner.flightplanner.mapping;

import ee.flightplanner.flightplanner.dto.SeatRowDto;
import ee.flightplanner.flightplanner.entity.SeatRowEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeatRowMapping {
    SeatRowDto seatRowToDto(SeatRowEntity seatRow);
    SeatRowEntity seatRowToEntity(SeatRowDto seatRowDto);
    List<SeatRowDto> seatRowListToDtoList(List<SeatRowEntity> seatRows);
    List<SeatRowEntity> seatRowListToEntityList(List<SeatRowDto> seatRowDtos);
}

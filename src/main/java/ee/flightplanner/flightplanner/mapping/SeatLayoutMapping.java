package ee.flightplanner.flightplanner.mapping;

import ee.flightplanner.flightplanner.dto.SeatLayoutDto;
import ee.flightplanner.flightplanner.entity.SeatLayoutEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeatLayoutMapping {
    SeatLayoutDto seatLayoutToDto(SeatLayoutEntity seatLayout);
    SeatLayoutEntity seatLayoutToEntity(SeatLayoutDto seatLayoutDto);
    List<SeatLayoutDto> seatLayoutListToDtoList(List<SeatLayoutEntity> seatLayouts);
    List<SeatLayoutEntity> seatLayoutListToEntityList(List<SeatLayoutDto> seatLayoutDtos);
}

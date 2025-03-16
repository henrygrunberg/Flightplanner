package ee.flightplanner.flightplanner.mapping;

import ee.flightplanner.flightplanner.dto.AirlineDto;
import ee.flightplanner.flightplanner.entity.AirlineEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AirlineMapping {
    AirlineDto airlineToDto(AirlineEntity airlineEntity);
    AirlineEntity airlineToEntity(AirlineDto airlineDto);
    List<AirlineDto> airlineListToDtoList(List<AirlineEntity> airlineEntities);
    List<AirlineEntity> airlineListToEntityList(List<AirlineDto> airlineDtos);
}

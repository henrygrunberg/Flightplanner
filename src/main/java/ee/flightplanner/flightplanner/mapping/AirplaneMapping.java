package ee.flightplanner.flightplanner.mapping;

import ee.flightplanner.flightplanner.dto.AirlineDto;
import ee.flightplanner.flightplanner.dto.AirplaneDto;
import ee.flightplanner.flightplanner.entity.AirlineEntity;
import ee.flightplanner.flightplanner.entity.AirplaneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AirplaneMapping {
    @Mapping(source = "airline.airlineId", target = "airlineId")
    AirplaneDto airplaneToDto(AirplaneEntity airplaneEntity);

    @Mapping(source = "airlineId", target = "airline.airlineId")
    AirplaneEntity airplaneToEntity(AirplaneDto airplaneDto);

    List<AirlineDto> airplaneListToDtoList(List<AirplaneEntity> airplaneEntities);
    List<AirlineEntity> airplaneListToEntityList(List<AirplaneDto> airplaneDtos);
}

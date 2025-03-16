package ee.flightplanner.flightplanner.mapping;

import ee.flightplanner.flightplanner.dto.FlightDto;
import ee.flightplanner.flightplanner.dto.booking.FlightTableDto;
import ee.flightplanner.flightplanner.entity.FlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlightMapping {

    @Mapping(source = "airline.name", target = "airlineName")
    @Mapping(source = "airplane.model", target = "airplaneModel")
    FlightDto flightToDto(FlightEntity flightEntity);

    @Mapping(target = "airline", ignore = true)
    @Mapping(target = "airplane", ignore = true)
    FlightEntity flightToEntity(FlightDto flightDto);

    List<FlightDto> flightListToDtoList(List<FlightEntity> flightEntities);
    List<FlightEntity> flightListToEntityList(List<FlightDto> flightDtos);

    @Mapping(source = "airline.name", target = "airlineName")
    @Mapping(source = "airplane.model", target = "airplaneModel")
    FlightTableDto flightTableToDtoTable(FlightEntity flight);

    List<FlightTableDto> flightTableListToDtoTableList(List<FlightEntity> flights);

}

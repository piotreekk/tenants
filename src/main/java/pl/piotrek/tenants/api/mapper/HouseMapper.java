package pl.piotrek.tenants.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.piotrek.tenants.api.dto.HouseDTO;
import pl.piotrek.tenants.model.House;

@Mapper(componentModel = "spring")
public interface HouseMapper {
    HouseMapper INSTANCE = Mappers.getMapper(HouseMapper.class);

    House houseDtoToHouse(HouseDTO houseDTO);
    HouseDTO houseToHouseDto(House house);

}

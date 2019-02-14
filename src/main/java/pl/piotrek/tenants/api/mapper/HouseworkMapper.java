package pl.piotrek.tenants.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.piotrek.tenants.api.dto.HouseworkDTO;
import pl.piotrek.tenants.entity.Housework;

@Mapper(componentModel = "spring")
public interface HouseworkMapper {
    HouseworkMapper INSTANCE = Mappers.getMapper(HouseworkMapper.class);

    Housework houseworkDtoToHousework(HouseworkDTO houseworkDTO);

    @Mapping(source = "house.id", target = "houseId")
    HouseworkDTO houseworkToHouseworkDTO(Housework housework);
}

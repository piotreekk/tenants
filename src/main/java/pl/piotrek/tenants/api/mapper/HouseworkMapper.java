package pl.piotrek.tenants.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import pl.piotrek.tenants.api.dto.HouseworkDTO;
import pl.piotrek.tenants.model.entity.Housework;

@Mapper(componentModel = "spring")
public interface HouseworkMapper {
    HouseworkMapper INSTANCE = Mappers.getMapper(HouseworkMapper.class);

    Housework fromDtoToEntity(HouseworkDTO houseworkDTO);

    @Mappings({
            @Mapping(source = "house.id", target = "houseId"),
            @Mapping(source = "createdDate",  target = "createdDate", dateFormat = "yyyy-MM-dd'T'HH:mm"),
            @Mapping(source = "updatedDate",  target = "updatedDate", dateFormat = "yyyy-MM-dd'T'HH:mm"),

    })
    HouseworkDTO fromEntityToDto(Housework housework);
}

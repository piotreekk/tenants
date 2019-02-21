package pl.piotrek.tenants.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.piotrek.tenants.api.dto.HouseworkRatingDTO;
import pl.piotrek.tenants.model.entity.HouseworkRating;

@Mapper(componentModel = "spring")
public interface HouseworkRatingMapper {
    HouseworkRatingMapper INSTANCE = Mappers.getMapper(HouseworkRatingMapper.class);

    @Mapping(source = "houseworkId", target = "housework.id")
    HouseworkRating dtoToEntity(HouseworkRatingDTO houseworkRatingDTO);

    @Mapping(source = "housework.id", target = "houseworkId")
    HouseworkRatingDTO entityToDto(HouseworkRating houseworkRating);

}

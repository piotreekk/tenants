package pl.piotrek.tenants.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.piotrek.tenants.api.dto.UserDTO;
import pl.piotrek.tenants.model.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDto(User user);
    User userDtoToUser(UserDTO userDto);

}

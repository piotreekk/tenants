package pl.piotrek.tenants.api.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.api.dto.UserDTO;

@Component
public class UserResourceAssembler implements ResourceAssembler<UserDTO, Resource<UserDTO>> {
    @Override
    public Resource<UserDTO> toResource(UserDTO userDTO) {
        return new Resource<>(userDTO);
    }
}

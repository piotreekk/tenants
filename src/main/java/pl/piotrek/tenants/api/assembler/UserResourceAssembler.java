package pl.piotrek.tenants.api.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.api.dto.UserDTO;
import pl.piotrek.tenants.controller.HouseController;
import pl.piotrek.tenants.controller.HouseworkController;
import pl.piotrek.tenants.controller.UserController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserResourceAssembler implements ResourceAssembler<UserDTO, Resource<UserDTO>> {
    @Override
    public Resource<UserDTO> toResource(UserDTO userDTO){
        return new Resource<>(userDTO,
                linkTo(methodOn(UserController.class).getUserInfo(userDTO.getId())).withSelfRel(),
                linkTo(methodOn(HouseController.class).getHouseListForUser(userDTO.getId())).withRel("houses"),
                linkTo(methodOn(HouseworkController.class).getUserHouseworks(userDTO.getId())).withRel("houseworks")
        );
    }
}

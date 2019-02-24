package pl.piotrek.tenants.api.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.api.dto.HouseDTO;
import pl.piotrek.tenants.controller.HouseController;
import pl.piotrek.tenants.controller.HouseworkController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class HouseResourceAssembler implements ResourceAssembler<HouseDTO, Resource<HouseDTO>> {

    @Override
    public Resource<HouseDTO> toResource(HouseDTO house) {
        return new Resource<>(house,
                linkTo(methodOn(HouseController.class).getHouseById(house.getId())).withSelfRel(),
                linkTo(methodOn(HouseworkController.class).getHouseworks(house.getId())).withRel("houseworks"),
                linkTo(methodOn(HouseworkController.class).createHousework(null, house.getId())).withRel("add_housework"),
                linkTo(methodOn(HouseController.class).getHouseList()).withRel("houses"));
    }
}

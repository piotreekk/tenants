package pl.piotrek.tenants.api.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.api.dto.HouseList;
import pl.piotrek.tenants.controller.HouseController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class HouseListResourceAssembler implements ResourceAssembler<HouseList, Resource<HouseList>> {

    @Override
    public Resource<HouseList> toResource(HouseList houses) {
        return new Resource<>(houses,
                linkTo(methodOn(HouseController.class).getHouseList()).withSelfRel());
    }
}

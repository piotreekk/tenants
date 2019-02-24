package pl.piotrek.tenants.api.assembler;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.api.dto.HouseworkDTO;
import pl.piotrek.tenants.controller.HouseController;
import pl.piotrek.tenants.controller.HouseworkController;
import pl.piotrek.tenants.model.HouseworkStatus;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class HouseworkResourceAssembler implements ResourceAssembler<HouseworkDTO, Resource<HouseworkDTO>> {

    @Override
    public Resource<HouseworkDTO> toResource(HouseworkDTO houseworkDTO) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(HouseworkController.class).getHouseworkById(houseworkDTO.getId())).withSelfRel());

        // OPTIONAL LINKS
        if(houseworkDTO.getStatus() == HouseworkStatus.TO_DO || houseworkDTO.getStatus() == HouseworkStatus.IN_PROGRESS)
            links.add(linkTo(methodOn(HouseworkController.class).assignUser(houseworkDTO.getId(), null)).withRel("assign"));
        if(houseworkDTO.getStatus() == HouseworkStatus.IN_PROGRESS)
            links.add(linkTo(methodOn(HouseworkController.class).finishHousework(houseworkDTO.getId())).withRel("finish"));

        links.add(linkTo(methodOn(HouseworkController.class).getHouseworks(houseworkDTO.getHouseId())).withRel("houseworks"));
        links.add(linkTo(methodOn(HouseController.class).getHouseById(houseworkDTO.getHouseId())).withRel("house"));

        return new Resource<>(houseworkDTO, links);

    }

}

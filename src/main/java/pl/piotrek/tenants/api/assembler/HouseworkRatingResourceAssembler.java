package pl.piotrek.tenants.api.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.api.dto.HouseworkRatingDTO;
import pl.piotrek.tenants.controller.HouseworkController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class HouseworkRatingResourceAssembler implements ResourceAssembler<HouseworkRatingDTO, Resource<HouseworkRatingDTO>> {

    @Override
    public Resource<HouseworkRatingDTO> toResource(HouseworkRatingDTO houseworkRatingDTO) {
        return new Resource<>(houseworkRatingDTO,
                linkTo(methodOn(HouseworkController.class).getRateById(houseworkRatingDTO.getId())).withSelfRel(),
                linkTo(methodOn(HouseworkController.class).getHouseworkRates(houseworkRatingDTO.getHouseworkId())).withRel("other_rates"),
                linkTo(methodOn(HouseworkController.class).getHouseworkById(houseworkRatingDTO.getHouseworkId())).withRel("housework")
                );
    }
}

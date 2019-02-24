package pl.piotrek.tenants.api.assembler;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.api.dto.UserRatingDTO;
import pl.piotrek.tenants.controller.UserController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class UserRatingResourceAssembler implements ResourceAssembler<UserRatingDTO, Resource<UserRatingDTO>> {
    @Override
    public Resource<UserRatingDTO> toResource(UserRatingDTO userRatingDTO) {
        return new Resource<>(userRatingDTO,
                linkTo(methodOn(UserController.class).getUserRating(userRatingDTO.getUserId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUserInfo(userRatingDTO.getUserId())).withRel("user_info")
                );
    }
}

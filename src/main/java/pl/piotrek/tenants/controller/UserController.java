package pl.piotrek.tenants.controller;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrek.tenants.api.assembler.UserRatingResourceAssembler;
import pl.piotrek.tenants.api.dto.UserRatingDTO;
import pl.piotrek.tenants.api.mapper.UserMapper;
import pl.piotrek.tenants.service.UserService;

@RestController
@RequestMapping(value = {"/api/user/", "/api/user"}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaTypes.HAL_JSON_VALUE})
public class UserController {
    private UserService userService;
    private UserMapper userMapper;
    private UserRatingResourceAssembler ratingAssembler;

    public UserController(UserService userService, UserMapper userMapper, UserRatingResourceAssembler ratingAssembler) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.ratingAssembler = ratingAssembler;
    }

    @GetMapping
    public Resource<UserRatingDTO> getUserRating(Long id){
        Double rate = userService.getRating(id);
        UserRatingDTO ratingDTO = new UserRatingDTO();
        ratingDTO.setRate(rate);

        return ratingAssembler.toResource(ratingDTO);
    }

    public String getUserInfo(Long xd){
        return  null;
    }

}

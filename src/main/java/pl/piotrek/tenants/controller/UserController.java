package pl.piotrek.tenants.controller;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrek.tenants.api.assembler.UserRatingResourceAssembler;
import pl.piotrek.tenants.api.assembler.UserResourceAssembler;
import pl.piotrek.tenants.api.dto.UserDTO;
import pl.piotrek.tenants.api.dto.UserInfo;
import pl.piotrek.tenants.api.dto.UserRatingDTO;
import pl.piotrek.tenants.api.mapper.UserMapper;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.security.CurrentUser;
import pl.piotrek.tenants.security.UserPrincipal;
import pl.piotrek.tenants.service.UserService;

@RestController
@RequestMapping(value = {"/api/user/", "/api/user"}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaTypes.HAL_JSON_VALUE})
public class UserController {
    private UserService userService;
    private UserMapper userMapper;
    private UserRatingResourceAssembler ratingAssembler;
    private UserResourceAssembler userAssembler;

    public UserController(UserService userService, UserMapper userMapper, UserRatingResourceAssembler ratingAssembler, UserResourceAssembler userAssembler) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.ratingAssembler = ratingAssembler;
        this.userAssembler = userAssembler;
    }

    @GetMapping("/{id}/rating")
    public Resource<UserRatingDTO> getUserRating(@PathVariable Long id){
        Double rate = userService.getUserRating(id);
        UserRatingDTO ratingDTO = new UserRatingDTO();
        ratingDTO.setRate(rate);

        return ratingAssembler.toResource(ratingDTO);
    }

    @GetMapping("{id}")
    public Resource<UserDTO> getUserInfo(@PathVariable Long id){
        User userEntity = userService.getUserById(id);
        UserDTO userDTO = userMapper.fromEntityToDto(userEntity);
        return userAssembler.toResource(userDTO);
    }

    @GetMapping("/me")
    public UserInfo getCurrentUserInfo(@CurrentUser UserPrincipal userPrincipal){
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userPrincipal.getEmail());
        userInfo.setFirstName(userPrincipal.getFirstName());
        userInfo.setLastName(userPrincipal.getLastName());

        return userInfo;
    }

}

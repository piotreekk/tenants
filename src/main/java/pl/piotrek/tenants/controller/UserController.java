package pl.piotrek.tenants.controller;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piotrek.tenants.api.mapper.UserMapper;
import pl.piotrek.tenants.service.UserService;

@RestController
@RequestMapping(value = {"/api/user/", "/api/user"}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaTypes.HAL_JSON_VALUE})
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }



}

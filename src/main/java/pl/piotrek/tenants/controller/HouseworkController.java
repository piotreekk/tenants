package pl.piotrek.tenants.controller;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotrek.tenants.api.assembler.HouseworkRatingResourceAssembler;
import pl.piotrek.tenants.api.assembler.HouseworkResourceAssembler;
import pl.piotrek.tenants.api.assembler.UserResourceAssembler;
import pl.piotrek.tenants.api.dto.*;
import pl.piotrek.tenants.api.mapper.HouseworkMapper;
import pl.piotrek.tenants.api.mapper.HouseworkRatingMapper;
import pl.piotrek.tenants.api.mapper.UserMapper;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;
import pl.piotrek.tenants.security.CurrentUser;
import pl.piotrek.tenants.security.UserPrincipal;
import pl.piotrek.tenants.service.HouseworkRatingService;
import pl.piotrek.tenants.service.HouseworkService;

import java.util.Collection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/housework", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaTypes.HAL_JSON_VALUE})
public class HouseworkController {
    private HouseworkService houseworkService;
    private HouseworkRatingService houseworkRatingService;

    private HouseworkMapper houseworkMapper;
    private HouseworkRatingMapper houseworkRatingMapper;
    private UserMapper userMapper;

    private HouseworkResourceAssembler houseworkAssembler;
    private HouseworkRatingResourceAssembler ratingAssembler;
    private UserResourceAssembler userResourceAssembler;

    public HouseworkController(HouseworkService houseworkService, HouseworkRatingService houseworkRatingService,
                               HouseworkResourceAssembler houseworkAssembler, HouseworkMapper houseworkMapper, HouseworkRatingMapper houseworkRatingMapper,
                               UserMapper userMapper, HouseworkRatingResourceAssembler ratingAssembler, UserResourceAssembler userResourceAssembler)
    {
        this.houseworkService = houseworkService;
        this.houseworkRatingService = houseworkRatingService;
        this.houseworkAssembler = houseworkAssembler;
        this.houseworkMapper = houseworkMapper;
        this.houseworkRatingMapper = houseworkRatingMapper;
        this.userMapper = userMapper;
        this.ratingAssembler = ratingAssembler;
        this.userResourceAssembler = userResourceAssembler;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkDTO> getHouseworkById(@PathVariable("id") Long houseworkId) {
        HouseworkDTO houseworkDTO = houseworkMapper.fromEntityToDto(houseworkService.getHousework(houseworkId));

        Double rateAVG = houseworkService.getAvgRatingForHousework(houseworkDTO.getId());

        houseworkDTO.setAvgRate(rateAVG);

        return houseworkAssembler.toResource(houseworkDTO);
    }

    @GetMapping("/house/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HouseworkList getHouseworks(@PathVariable("id") Long houseId) {

        HouseworkList houseworkList = new HouseworkList();

        houseworkService.getHouseworksOf(houseId)
                .stream()
                .map(houseworkMapper::fromEntityToDto)
                .map(houseworkDTO -> {
                    houseworkDTO.setAvgRate(houseworkService.getAvgRatingForHousework(houseworkDTO.getId()));
                    return houseworkDTO;
                })
                .map(houseworkAssembler::toResource)
                .forEach(houseworkList::addHousework);

        houseworkList.add(linkTo(methodOn(HouseworkController.class).getHouseworks(houseId)).withSelfRel());

        return houseworkList;
    }

    @PostMapping("/house/{id}")
    public ResponseEntity<?> createHousework(@RequestBody HouseworkDTO houseworkDTO, @PathVariable("id") Long houseId){
        Housework savedEntity = houseworkService.addHousework(houseworkMapper.fromDtoToEntity(houseworkDTO), houseId);
        HouseworkDTO savedDto = houseworkMapper.fromEntityToDto(savedEntity);
        return ResponseEntity
                .created(linkTo(methodOn(HouseworkController.class).getHouseworkById(savedDto.getId())).toUri())
                .body(houseworkAssembler.toResource(savedDto));
    }


    @GetMapping("/user/current")
    public HouseworkList getCurrentUserHouseworks(@CurrentUser UserPrincipal userPrincipal){
        HouseworkList houseworkList = new HouseworkList();
        houseworkService.getUserHouseworks(userPrincipal.getId())
                .stream()
                .map(houseworkMapper::fromEntityToDto)
                .map(houseworkAssembler::toResource)
                .forEach(houseworkList::addHousework);

        houseworkList.add(linkTo(methodOn(HouseworkController.class).getCurrentUserHouseworks(userPrincipal)).withSelfRel());

        return houseworkList;
    }

    @GetMapping("/user/{id}")
    public HouseworkList getUserHouseworks(@PathVariable Long id){
        HouseworkList houseworkList = new HouseworkList();
        houseworkService.getUserHouseworks(id)
                .stream()
                .map(houseworkMapper::fromEntityToDto)
                .map(houseworkAssembler::toResource)
                .forEach(houseworkList::addHousework);

        houseworkList.add(linkTo(methodOn(HouseworkController.class).getUserHouseworks(id)).withSelfRel());

        return houseworkList;
    }


    @PostMapping("/{id}/assign")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkDTO> assignUser(@PathVariable("id") Long houseworkId, @CurrentUser UserPrincipal userPrincipal ) {
        Long userId = userPrincipal.getId();
        HouseworkDTO houseworkDTO = houseworkMapper.fromEntityToDto(houseworkService.assignUserToHousework(houseworkId, userId));
        return houseworkAssembler.toResource(houseworkDTO);
    }

    @PostMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkDTO> finishHousework(@PathVariable("id") Long houseworkId) {
        HouseworkDTO houseworkDTO = houseworkMapper.fromEntityToDto(houseworkService.finishHousework(houseworkId));
        return houseworkAssembler.toResource(houseworkDTO);
    }


    @PostMapping("/{id}/rate")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkRatingDTO> rateHousework(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal, @RequestBody HouseworkRatingDTO houseworkRatingDTO){
        HouseworkRating houseworkRatingEntity = houseworkRatingMapper.fromDtoToEntity(houseworkRatingDTO);
        HouseworkRatingDTO response= houseworkRatingMapper.fromEntityToDto(houseworkService.rateHousework(id, userPrincipal.getId(), houseworkRatingEntity));
        return ratingAssembler.toResource(response);
    }

    @GetMapping("{id}/rates")
    @ResponseStatus(HttpStatus.OK)
    public HouseworkRatingList getHouseworkRates(@PathVariable("id") Long houseworkId){
        HouseworkRatingList ratingList = new HouseworkRatingList();
        Collection<HouseworkRating> ratingSet = houseworkService.getRatingsForHouswork(houseworkId);
        ratingSet.stream()
                .map(houseworkRatingMapper::fromEntityToDto)
                .map(ratingAssembler::toResource)
                .forEach(ratingList::addRating);

        ratingList.add(linkTo(methodOn(HouseworkController.class).getHouseworkRates(houseworkId)).withSelfRel());

        return ratingList;
    }

    @GetMapping("/rate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkRatingDTO> getRateById(@PathVariable Long id) {
        HouseworkRatingDTO ratingDTO = houseworkRatingMapper.fromEntityToDto(houseworkRatingService.getRatingById(id));
        return ratingAssembler.toResource(ratingDTO);
    }

    @GetMapping("/{id}/users")
    public UserList getHouseworkUsers(@PathVariable("id") Long houseworkId){
        UserList userList = new UserList();
        houseworkService.getHouseworkUsers(houseworkId).stream()
                .map(userMapper::fromEntityToDto)
                .map(userResourceAssembler::toResource)
                .forEach(userList::addUser);

        userList.add(linkTo(methodOn(HouseworkController.class).getHouseworkUsers(houseworkId)).withSelfRel());

        return userList;
    }
}
package pl.piotrek.tenants.controller;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.piotrek.tenants.api.assembler.HouseworkRatingResourceAssembler;
import pl.piotrek.tenants.api.assembler.HouseworkResourceAssembler;
import pl.piotrek.tenants.api.dto.HouseworkDTO;
import pl.piotrek.tenants.api.dto.HouseworkList;
import pl.piotrek.tenants.api.dto.HouseworkRatingDTO;
import pl.piotrek.tenants.api.dto.HouseworkRatingList;
import pl.piotrek.tenants.api.mapper.HouseworkMapper;
import pl.piotrek.tenants.api.mapper.HouseworkRatingMapper;
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
    private HouseworkResourceAssembler assembler;
    private HouseworkMapper houseworkMapper;
    private HouseworkRatingMapper houseworkRatingMapper;
    private HouseworkRatingResourceAssembler ratingAssembler;

    public HouseworkController(HouseworkService houseworkService, HouseworkRatingService houseworkRatingService,
                               HouseworkResourceAssembler assembler, HouseworkMapper houseworkMapper, HouseworkRatingMapper houseworkRatingMapper,
                               HouseworkRatingResourceAssembler ratingAssembler) {
        this.houseworkService = houseworkService;
        this.houseworkRatingService = houseworkRatingService;
        this.assembler = assembler;
        this.houseworkMapper = houseworkMapper;
        this.houseworkRatingMapper = houseworkRatingMapper;
        this.ratingAssembler = ratingAssembler;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkDTO> getHousework(@PathVariable Long id) {
        HouseworkDTO houseworkDTO = houseworkMapper.houseworkToHouseworkDTO(houseworkService.getHousework(id));
        return assembler.toResource(houseworkDTO);
    }

    @GetMapping("/house/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HouseworkList getHouseworks(@PathVariable("id") Long houseId) {
        HouseworkList houseworkList = new HouseworkList();
        houseworkService.getHouseworksOf(houseId)
                .stream()
                .map(houseworkMapper::houseworkToHouseworkDTO)
                .map(assembler::toResource)
                .forEach(houseworkList::addHousework);

        houseworkList.add(linkTo(methodOn(HouseworkController.class).getHouseworks(houseId)).withSelfRel());

        return houseworkList;
    }


    @GetMapping("/user/current")
    public HouseworkList getCurrentUserHouseworks(@CurrentUser UserPrincipal userPrincipal){
        HouseworkList houseworkList = new HouseworkList();
        houseworkService.getUserHouseworks(userPrincipal.getId())
                .stream()
                .map(houseworkMapper::houseworkToHouseworkDTO)
                .map(assembler::toResource)
                .forEach(houseworkList::addHousework);

        houseworkList.add(linkTo(methodOn(HouseworkController.class).getCurrentUserHouseworks(userPrincipal)).withSelfRel());

        return houseworkList;
    }

    @GetMapping("/user/{id}")
    public HouseworkList getUserHouseworks(@PathVariable Long id){
        HouseworkList houseworkList = new HouseworkList();
        houseworkService.getUserHouseworks(id)
                .stream()
                .map(houseworkMapper::houseworkToHouseworkDTO)
                .map(assembler::toResource)
                .forEach(houseworkList::addHousework);

        houseworkList.add(linkTo(methodOn(HouseworkController.class).getUserHouseworks(id)).withSelfRel());

        return houseworkList;
    }

    @PostMapping("/{id}/assign")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkDTO> assignUser(@PathVariable("id") Long houseworkId, @CurrentUser UserPrincipal userPrincipal ) {
        Long userId = userPrincipal.getId();
        HouseworkDTO houseworkDTO = houseworkMapper.houseworkToHouseworkDTO(houseworkService.assignUserToHousework(houseworkId, userId));
        return assembler.toResource(houseworkDTO);
    }

    @PostMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkDTO> finishHousework(@PathVariable("id") Long houseworkId) {
        HouseworkDTO houseworkDTO = houseworkMapper.houseworkToHouseworkDTO(houseworkService.finishHousework(houseworkId));
        return assembler.toResource(houseworkDTO);
    }


    @PostMapping("/{id}/rate")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkRatingDTO> rateHousework(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal, @RequestBody HouseworkRatingDTO houseworkRatingDTO){
        HouseworkRating houseworkRatingEntity = houseworkRatingMapper.dtoToEntity(houseworkRatingDTO);
        HouseworkRatingDTO response= houseworkRatingMapper.entityToDto(houseworkService.rateHousework(id, userPrincipal.getId(), houseworkRatingEntity));
        return ratingAssembler.toResource(response);
    }

    @GetMapping("{id}/rates")
    @ResponseStatus(HttpStatus.OK)
    public HouseworkRatingList getRates(@PathVariable("id") Long houseworkId){
        HouseworkRatingList ratingList = new HouseworkRatingList();
        Collection<HouseworkRating> ratingSet = houseworkService.getRatingsForHouswork(houseworkId);
        ratingSet.stream()
                .map(houseworkRatingMapper::entityToDto)
                .map(ratingAssembler::toResource)
                .forEach(ratingList::addRating);

        return ratingList;
    }

    @GetMapping("/rate/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkRatingDTO> getRateById(@PathVariable Long id) {
        HouseworkRatingDTO ratingDTO = houseworkRatingMapper.entityToDto(houseworkRatingService.getRatingById(id));
        return ratingAssembler.toResource(ratingDTO);
    }
}
package pl.piotrek.tenants.controller;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.piotrek.tenants.api.assembler.HouseworkResourceAssembler;
import pl.piotrek.tenants.api.dto.HouseworkDTO;
import pl.piotrek.tenants.api.dto.HouseworkList;
import pl.piotrek.tenants.api.mapper.HouseworkMapper;
import pl.piotrek.tenants.service.HouseworkService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/housework", produces = MediaTypes.HAL_JSON_VALUE)
public class HouseworkController {
    private HouseworkService houseworkService;
    private HouseworkResourceAssembler assembler;
    private HouseworkMapper mapper;

    public HouseworkController(HouseworkService houseworkService, HouseworkResourceAssembler assembler, HouseworkMapper mapper) {
        this.houseworkService = houseworkService;
        this.assembler = assembler;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public Resource<HouseworkDTO> getHousework(@PathVariable Long id) {
        HouseworkDTO houseworkDTO = mapper.houseworkToHouseworkDTO(houseworkService.getHousework(id));
        return assembler.toResource(houseworkDTO);
//        return houseworkDTO;
    }

    @GetMapping("/house/{id}")
    public HouseworkList getHouseworks(@PathVariable("id") Long houseId) {
        List<Resource<HouseworkDTO>> list = houseworkService.getHouseworksOf(houseId)
                .stream()
                .map(mapper::houseworkToHouseworkDTO)
                .map(assembler::toResource)
                .collect(Collectors.toList());

        HouseworkList houseworkList = new HouseworkList();
        houseworkList.setHouseworks(list);
        houseworkList.add(linkTo(methodOn(HouseworkController.class).getHouseworks(houseId)).withSelfRel());

        return houseworkList;
    }

    @PostMapping("/{id}/assign")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkDTO> assignUser(@PathVariable("id") Long houseworkId, @RequestParam Long userId ) {
        HouseworkDTO houseworkDTO = mapper.houseworkToHouseworkDTO(houseworkService.assignUserToHousework(houseworkId, userId));
        return assembler.toResource(houseworkDTO);
    }

    @PostMapping("/{id}/finish")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseworkDTO> finishHousework(@PathVariable("id") Long houseworkId) {
        HouseworkDTO houseworkDTO = mapper.houseworkToHouseworkDTO(houseworkService.finishHousework(houseworkId));
        return assembler.toResource(houseworkDTO);
    }

}

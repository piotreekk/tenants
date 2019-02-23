package pl.piotrek.tenants.controller;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotrek.tenants.api.assembler.HouseResourceAssembler;
import pl.piotrek.tenants.api.dto.HouseDTO;
import pl.piotrek.tenants.api.dto.HouseList;
import pl.piotrek.tenants.api.mapper.HouseMapper;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.service.HouseService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
@RestController
@RequestMapping(value = "/api/house", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaTypes.HAL_JSON_VALUE})
public class    HouseController {
    private HouseService houseService;
    private HouseMapper houseMapper;
    private HouseResourceAssembler assembler;

    public HouseController(HouseService houseService, HouseMapper houseMapper, HouseResourceAssembler assembler) {
        this.houseService = houseService;
        this.houseMapper = houseMapper;
        this.assembler = assembler;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public HouseList getHouseList(){
        List<Resource<HouseDTO>> housesList = houseService.getAll().stream()
                .map(houseMapper::houseToHouseDto)
                .map(assembler::toResource)
                .collect(Collectors.toList());
        HouseList resultList = new HouseList();
        resultList.setHouses(housesList);
        resultList.add(linkTo(methodOn(HouseController.class).getHouseList()).withSelfRel());

        return resultList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Resource<HouseDTO> getHouseById(@PathVariable Long id){
        return assembler.toResource(houseMapper.houseToHouseDto(houseService.getById(id)));
    }


    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HouseList getHouseListForUser(@PathVariable Long id){
        List<Resource<HouseDTO>> housesList = houseService.getUserHouses(id).stream()
                .map(houseMapper::houseToHouseDto)
                .map(assembler::toResource)
                .collect(Collectors.toList());

        HouseList resultList = new HouseList();
        resultList.setHouses(housesList);
        resultList.add(linkTo(methodOn(HouseController.class).getHouseListForUser(id)).withSelfRel());

        return resultList;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Resource<HouseDTO>> addHouse(@Valid @RequestBody HouseDTO houseDTO) {
        House houseEntity = houseMapper.houseDtoToHouse(houseDTO);
        HouseDTO created = houseMapper.houseToHouseDto(houseService.addHouse(houseEntity));
        return ResponseEntity.created(linkTo(methodOn(HouseController.class).getHouseById(created.getId())).toUri())
                .body(assembler.toResource(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHouse(@PathVariable Long id){
        houseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

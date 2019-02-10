package pl.piotrek.tenants.controller;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piotrek.tenants.api.assembler.HouseResourceAssembler;
import pl.piotrek.tenants.api.dto.HouseDTO;
import pl.piotrek.tenants.api.mapper.HouseMapper;
import pl.piotrek.tenants.model.House;
import pl.piotrek.tenants.service.HouseService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/house", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

public class HouseController {
    private HouseService houseService;
    private HouseMapper houseMapper;
    private HouseResourceAssembler assembler;

    public HouseController(HouseService houseService, HouseMapper houseMapper, HouseResourceAssembler assembler) {
        this.houseService = houseService;
        this.houseMapper = houseMapper;
        this.assembler = assembler;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getHouseList(){
        List<Resource<HouseDTO>> response = houseService.getAll().stream()
                .map(houseMapper::houseToHouseDto)
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource<HouseDTO>> getHouseById(@PathVariable Long id){
        HouseDTO response = houseMapper.houseToHouseDto(houseService.getById(id));
        return new ResponseEntity<>(assembler.toResource(response), HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<?> addHouse(@RequestBody HouseDTO houseDTO){
        House houseEntity = houseMapper.houseDtoToHouse(houseDTO);
        HouseDTO created = houseMapper.houseToHouseDto(houseService.addHouse(houseEntity));

        return ResponseEntity
                .created(linkTo(methodOn(HouseController.class).getHouseById(created.getId())).toUri())
                .body(assembler.toResource(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHouse(@RequestBody HouseDTO houseDTO){

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

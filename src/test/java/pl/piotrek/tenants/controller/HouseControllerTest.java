package pl.piotrek.tenants.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.piotrek.tenants.api.assembler.HouseResourceAssembler;
import pl.piotrek.tenants.api.mapper.HouseMapper;
import pl.piotrek.tenants.model.House;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.service.HouseService;
import pl.piotrek.tenants.service.impl.HouseServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HouseControllerTest {

    @Mock
    HouseService houseService;

    @Mock
    HouseResourceAssembler assembler;

    @Spy
    HouseMapper houseMapper = HouseMapper.INSTANCE;

    @InjectMocks
    HouseController houseController;

    MockMvc mockMvc;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
//        houseController = new HouseController(houseService, houseMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(houseController).build();
    }

    @Test
    public void testListHouses() throws Exception {
        House house1 = new House();
        house1.setId(Long.valueOf(1));
        house1.setAddress("Gleboka 22");
        house1.setCity("Lublin");

        House house2 = new House();

        house2.setId(Long.valueOf(2));
        house2.setAddress("Akademicka 9");
        house2.setCity("Lublin");

        List<House> houses = Arrays.asList(house1, house2);

        when(houseService.getAll()).thenReturn(houses);
//        given(houseService.getAll()).willReturn(houses);

        mockMvc.perform(get("/api/house/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testHouseById() throws Exception {
        // given
        House house = new House();
        house.setId(1L);
        house.setCity("Lublin");
        house.setAddress("Akademicka 9");

        when(houseService.getById(anyLong())).thenReturn(house);

        mockMvc.perform(get("/api/house/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//                .andReturn().getResponse().getContentAsString();
                .andExpect(jsonPath("$.city").value("Lublin"));

    }


}

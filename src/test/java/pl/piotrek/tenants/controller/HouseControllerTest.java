package pl.piotrek.tenants.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.piotrek.tenants.api.assembler.HouseResourceAssembler;
import pl.piotrek.tenants.api.dto.HouseDTO;
import pl.piotrek.tenants.api.mapper.HouseMapper;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.service.HouseService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HouseController.class)
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

        mockMvc = MockMvcBuilders.standaloneSetup(houseController).build();
    }

    @Test
    public void testListHouses() throws Exception {
        //given
        House house1 = new House();
        house1.setId(1L);

        House house2 = new House();
        house2.setId(2L);


        // when
        when(houseService.getAll()).thenReturn(List.of(house1, house2));


        // then
        mockMvc.perform(get("/api/house/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.houses", hasSize(2)));

        verify(houseService, times(1)).getAll();
    }

    @Test
    public void testHouseById() throws Exception {
        // given
        House house = new House();
        house.setId(1L);
        house.setCity("Lublin");
        house.setAddress("Akademicka 9");

        // when
        when(houseService.getById(anyLong())).thenReturn(house);

        // then
        mockMvc.perform(get("/api/house/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testAddHose() throws Exception {
        // given
        HouseDTO houseDTO = new HouseDTO();
        houseDTO.setId(1L);
        houseDTO.setCity("Lublin");
        houseDTO.setAddress("Gleboka");

        String json = getJsonFromObject(houseDTO);

        when(houseService.addHouse(any())).thenReturn(new House());


        // when
        mockMvc.perform(
                post("/api/house")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
            .andExpect(status().isCreated());


        verify(houseService, times(1)).addHouse(any());
    }

    private String getJsonFromObject(Object object){
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }


}

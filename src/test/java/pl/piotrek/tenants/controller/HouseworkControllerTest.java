package pl.piotrek.tenants.controller;

import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import pl.piotrek.tenants.api.assembler.HouseworkRatingResourceAssembler;
import pl.piotrek.tenants.api.assembler.HouseworkResourceAssembler;
import pl.piotrek.tenants.api.mapper.HouseworkMapper;
import pl.piotrek.tenants.api.mapper.HouseworkRatingMapper;
import pl.piotrek.tenants.service.HouseworkRatingService;
import pl.piotrek.tenants.service.HouseworkService;

public class HouseworkControllerTest {
    MockMvc mockMvc;

    @Mock
    HouseworkService houseworkService;

    @Mock
    HouseworkRatingService houseworkRatingService;

    HouseworkResourceAssembler assembler;

    HouseworkMapper houseworkMapper;

    HouseworkRatingMapper houseworkRatingMapper;

    HouseworkRatingResourceAssembler ratingAssembler;


    public void setUp(){
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup();

    }


}

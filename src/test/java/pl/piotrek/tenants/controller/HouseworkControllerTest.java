package pl.piotrek.tenants.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.piotrek.tenants.api.assembler.HouseworkRatingResourceAssembler;
import pl.piotrek.tenants.api.assembler.HouseworkResourceAssembler;
import pl.piotrek.tenants.api.assembler.UserResourceAssembler;
import pl.piotrek.tenants.api.mapper.HouseworkMapper;
import pl.piotrek.tenants.api.mapper.HouseworkRatingMapper;
import pl.piotrek.tenants.api.mapper.UserMapper;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.service.HouseworkRatingService;
import pl.piotrek.tenants.service.HouseworkService;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HouseworkControllerTest {
    MockMvc mockMvc;

    @Mock
    HouseworkService houseworkService;

    @Mock
    HouseworkRatingService houseworkRatingService;

    HouseworkResourceAssembler houseworkAssembler = new HouseworkResourceAssembler();

    HouseworkMapper houseworkMapper = HouseworkMapper.INSTANCE;

    HouseworkRatingMapper houseworkRatingMapper = HouseworkRatingMapper.INSTANCE;

    HouseworkRatingResourceAssembler ratingAssembler = new HouseworkRatingResourceAssembler();

    UserMapper userMapper = UserMapper.INSTANCE;

    UserResourceAssembler userAssembler = new UserResourceAssembler();

    HouseworkController houseworkController;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        houseworkController = new HouseworkController(houseworkService, houseworkRatingService, houseworkAssembler, houseworkMapper, houseworkRatingMapper, userMapper, ratingAssembler, userAssembler);
        mockMvc = MockMvcBuilders.standaloneSetup(houseworkController).build();

    }


    @Test
    public void getHouseworkByIdTest() throws Exception {
        Housework housework = new Housework();
        housework.setId(1L);
        housework.setName("Housework");

        when(houseworkService.getHousework(anyLong())).thenReturn(housework);

        mockMvc.perform(get("/api/housework/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("Housework")));

        verify(houseworkService, times(1)).getAvgRatingForHousework(anyLong());
    }


    @Test
    public void getAllHouseworksTest() throws Exception {
        List<Housework> houseworks = List.of(new Housework(), new Housework(), new Housework());

        when(houseworkService.getHouseworksOf(anyLong())).thenReturn(houseworks);

        mockMvc.perform(get("/api/housework/house/1"))
                .andExpect(status().isOk());

        verify(houseworkService, times(houseworks.size())).getAvgRatingForHousework(anyLong());
    }

    @Test
    public void getUsersPerformedHouseworkTest() throws Exception {
        User user1 = new User();
        user1.setFirstName("USER1");

        User user2 = new User();
        user2.setFirstName("USER2");

        List<User> users = List.of(user1, user2);


        when(houseworkService.getHouseworkUsers(anyLong())).thenReturn(users);

        mockMvc.perform(get("/api/housework/1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(users.size())))
                .andExpect(jsonPath("$.users[0].firstName", equalTo(user1.getFirstName())))
                .andDo(MockMvcResultHandlers.print());

        verify(houseworkService, times(1)).getHouseworkUsers(anyLong());
    }
}

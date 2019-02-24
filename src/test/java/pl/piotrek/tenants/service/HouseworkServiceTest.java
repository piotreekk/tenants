package pl.piotrek.tenants.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.piotrek.tenants.model.HouseworkStatus;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.repository.HouseworkRepository;
import pl.piotrek.tenants.repository.UserRepository;
import pl.piotrek.tenants.service.impl.HouseworkServiceImpl;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class HouseworkServiceTest {
    public static final String HOUSEWORK_NAME = "Some Housework";
    public static final String COMMENT = "Good Job!";
    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "Jan";
    public static final int RATE = 5;

    private HouseworkService houseworkService;

    @Mock
    private HouseworkRepository houseworkRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HouseRepository houseRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        houseworkService = new HouseworkServiceImpl(houseworkRepository, userRepository, houseRepository);
    }

    @Test
    public void ratingTest(){
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName(USER_NAME);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Housework housework = new Housework();
        housework.setName(HOUSEWORK_NAME);
        housework.setStatus(HouseworkStatus.FINISHED);

        when(houseworkRepository.findById(anyLong())).thenReturn(Optional.of(housework));

        HouseworkRating rating = new HouseworkRating();
        rating.setComment(COMMENT);
        rating.setRate(RATE);

        HouseworkRating result = houseworkService.rateHousework(1L, USER_ID, rating);

        assertNotNull(result);
        assertNotNull(result.getHousework());
        assertEquals(result.getUser(), user);
        assertEquals(result.getHousework(), housework);
    }
}

package pl.piotrek.tenants.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.repository.HouseworkRepository;
import pl.piotrek.tenants.repository.UserRepository;
import pl.piotrek.tenants.service.impl.HouseworkServiceImpl;
import pl.piotrek.tenants.util.HouseworkStatus;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class HouseworkServiceTest {
    private final Long USER_ID=1L;
    private final String USER_NAME="Jan";

    private HouseworkService houseworkService;

    @Mock
    private HouseworkRepository houseworkRepository;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        houseworkService = new HouseworkServiceImpl(houseworkRepository, userRepository);
    }

    @Test
    public void ratingTest(){
        User user = new User();
        user.setId(USER_ID);
        user.setFirstName(USER_NAME);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Housework housework = new Housework();
        housework.setName("Some Housework");
        housework.setStatus(HouseworkStatus.FINISHED);

        when(houseworkRepository.findById(anyLong())).thenReturn(Optional.of(housework));

        HouseworkRating rating = new HouseworkRating();
        rating.setComment("Good Job!");
        rating.setRate(5);

        HouseworkRating result = houseworkService.rateHousework(1L, USER_ID, rating);

        assertNotNull(result);
        assertNotNull(result.getHousework());
        assertEquals(result.getUser(), user);
        assertEquals(result.getHousework(), housework);
    }
}

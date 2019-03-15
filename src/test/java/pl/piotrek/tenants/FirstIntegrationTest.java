package pl.piotrek.tenants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.service.HouseService;
import pl.piotrek.tenants.service.HouseworkService;
import pl.piotrek.tenants.service.UserService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FirstIntegrationTest {
    public static final String U1_EMAIL = "user1@email.pl";
    public static final String U1_PASSWORD = "123445678";

    public static final String U2_EMAIL = "user2@email.pl";
    public static final String U2_PASSWORD = "12345678";

    public static final int RATE_1 = 5;
    public static final int RATE_2 = 1;
    public static final int EXPERCTED_USER_RATE_AVG = (RATE_1 + RATE_2) / 2;

    public Long HOUSE_ID;
    public Long HOUSEWORK_ID;


    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseworkService houseworkService;

    @Before
    public void initData(){
        addHouseWithInhabitants();
        addFinishedHouseworksWithRates();
    }

    @Test
    public void userRateTest(){
        User user = userService.getByEmail(U1_EMAIL);
        Double rate = userService.getRating(user.getId());

        assertEquals(rate, Double.valueOf(EXPERCTED_USER_RATE_AVG));
    }

    @Test
    public void houseworkRateTest(){
        Double houseworkAvgRate = houseworkService.getAvgRatingForHousework(HOUSEWORK_ID);
        assertEquals(Double.valueOf(RATE_1), houseworkAvgRate);
    }

    private void addHouseWithInhabitants(){
        House house = new House();

        User user = new User();
        user.setEmail(U1_EMAIL);
        user.setPassword(U1_PASSWORD);

        userService.addUser(user);


        User user1 = new User();
        user1.setEmail(U2_EMAIL);
        user1.setPassword(U2_PASSWORD);

        userService.addUser(user1);


        house.addInhabitant(user);
        house.addInhabitant(user1);

        House saved = houseService.addHouse(house);

        HOUSE_ID = saved.getId();
    }


    public void addFinishedHouseworksWithRates(){
        User userWhoIsRated = userService.getByEmail(U1_EMAIL);
        User userWhoRate = userService.getByEmail(U2_EMAIL);

        House house = houseService.getById(HOUSE_ID);

        Housework housework = new Housework();
        housework.setHouse(house);

        Housework saved1 = houseworkService.addHousework(housework);

        houseworkService.assignUserToHousework(saved1.getId(), userWhoIsRated.getId());

        houseworkService.finishHousework(saved1.getId());

        HouseworkRating rating0 = new HouseworkRating();
        rating0.setRate(RATE_1);
        rating0.setHousework(saved1);

        houseworkService.rateHousework(saved1.getId(), userWhoRate.getId(), rating0);


        // #########################################################################
        // Second housework

        Housework housework1 = new Housework();
        housework1.setHouse(house);

        Housework saved2 = houseworkService.addHousework(housework1);

        houseworkService.assignUserToHousework(saved2.getId(), userWhoIsRated.getId());

        houseworkService.finishHousework(saved2.getId());

        HouseworkRating rating1 = new HouseworkRating();
        rating1.setRate(RATE_2);
        rating1.setHousework(saved2);

        houseworkService.rateHousework(saved2.getId(), userWhoRate.getId(), rating1);


        // FOR USE IN SECOND TEST
        HOUSEWORK_ID = saved1.getId();
    }



}


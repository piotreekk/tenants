package pl.piotrek.tenants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.HouseworkRating;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.service.HouseService;
import pl.piotrek.tenants.service.HouseworkService;
import pl.piotrek.tenants.service.UserService;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FirstIntegrationTest {

    public static final String ADDRESS = "Deep Street 44";
    public static final String CITY = "Lublin";
    public static final String U1_FIRST_NAME = "Jan";
    public static final String U1_LAST_NAME = "Kowalski";
    public static final String U1_EMAIL = "user0@wp.pl";
    public static final String u1_PASSWORD = "123445677";
    public static final String U2_FIRST_NAME = "Kacper";
    public static final String U2_LAST_NAME = "Kowal";
    public static final String U2_EMAIL = "user11@wp.pl";
    public static final String U2_PASSWORD = "12345677";
    public static final String HOUSEWORK_NAME = "Housework0";
    public static final String HOUSEWORK_DESCRIPTION = "Description of housework0";
    public static final String R1_COMMENT = "Nice";
    public static final String H1_NAME = "Housework1";
    public static final String H1_DESCRIPTION = "Description of housework1";
    public static final int RATE_1 = 5;
    public static final int RATE_2 = 1;
    public static final String R2_COMMENT = "Bad";
    public static final int RATE_AVG = (RATE_1 + RATE_2) / 2;
    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseworkService houseworkService;

    @Test
    public void userRateTest(){
        addHouseWithInhabitants();
        addHouseworksFinishedWithRates();
        checkUserRateAvg();
    }

    private void addHouseWithInhabitants(){
        House house = new House();
        house.setAddress(ADDRESS);
        house.setCity(CITY);

        User user = new User();
        user.setFirstName(U1_FIRST_NAME);
        user.setLastName(U1_LAST_NAME);
        user.setEmail(U1_EMAIL);
        user.setPassword(u1_PASSWORD);

        userService.addUser(user);

        User user1 = new User();
        user1.setFirstName(U2_FIRST_NAME);
        user1.setLastName(U2_LAST_NAME);
        user1.setEmail(U2_EMAIL);
        user1.setPassword(U2_PASSWORD);

        userService.addUser(user1);

        house.addInhabitant(user);
        house.addInhabitant(user1);

        houseService.addHouse(house);
    }


    @Transactional
    public void addHouseworksFinishedWithRates(){
        User userWhoIsRated = userService.getByEmail(U1_EMAIL);
        User userWhoRate = userService.getByEmail(U2_EMAIL);
        House house = houseService.getByAddress(ADDRESS);

        Housework housework = new Housework();
        housework.setName(HOUSEWORK_NAME);
        housework.setDescription(HOUSEWORK_DESCRIPTION);
        housework.setHouse(house);

        Housework saved = houseworkService.addHousework(housework);
        houseworkService.assignUserToHousework(saved.getId(), userWhoIsRated.getId());
        houseworkService.finishHousework(saved.getId());

        HouseworkRating rating0 = new HouseworkRating();
        rating0.setRate(RATE_1);
        rating0.setComment(R1_COMMENT);

        houseworkService.rateHousework(saved.getId(), userWhoRate.getId(), rating0);


        // #########################################################################
        // Second housework

        Housework housework1 = new Housework();
        housework1.setName(H1_NAME);
        housework1.setDescription(H1_DESCRIPTION);
        housework1.setHouse(house);

        saved = houseworkService.addHousework(housework1);
        houseworkService.assignUserToHousework(saved.getId(), userWhoIsRated.getId());
        houseworkService.finishHousework(saved.getId());

        HouseworkRating rating1 = new HouseworkRating();
        rating1.setRate(RATE_2);
        rating1.setComment(R2_COMMENT);
        housework1.addRateToHousework(rating1);

        houseworkService.rateHousework(saved.getId(), userWhoRate.getId(), rating1);
    }


    private void checkUserRateAvg() {
        User user = userService.getByEmail(U1_EMAIL);
        Double rate = userService.getRating(user.getId());

        assertEquals(rate, Double.valueOf(RATE_AVG));
    }
}


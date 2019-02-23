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
        house.setAddress("Deep Street 44");
        house.setCity("Lublin");

        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("user0@wp.pl");
        user.setPassword("123445677");

        userService.addUser(user);

        User user1 = new User();
        user1.setFirstName("Kacper");
        user1.setLastName("Kowal");
        user1.setEmail("user11@wp.pl");
        user1.setPassword("12345677");

        userService.addUser(user1);

        house.addInhabitant(user);
        house.addInhabitant(user1);

        houseService.addHouse(house);
    }


    @Transactional
    public void addHouseworksFinishedWithRates(){
        User userWhoIsRated = userService.getByEmail("user0@wp.pl");
        User userWhoRate = userService.getByEmail("user11@wp.pl");
        House house = houseService.getByAddress("Deep Street 44");

        Housework housework = new Housework();
        housework.setName("Housework0");
        housework.setDescription("Description of housework0");
        housework.setHouse(house);

        Housework saved = houseworkService.addHousework(housework);
        houseworkService.assignUserToHousework(saved.getId(), userWhoIsRated.getId());
        houseworkService.finishHousework(saved.getId());

        HouseworkRating rating0 = new HouseworkRating();
        rating0.setRate(5);
        rating0.setComment("Nice");

        houseworkService.rateHousework(saved.getId(), userWhoRate.getId(), rating0);


        // #########################################################################
        // Second housework

        Housework housework1 = new Housework();
        housework1.setName("Housework1");
        housework1.setDescription("Description of housework1");
        housework1.setHouse(house);

        saved = houseworkService.addHousework(housework1);
        houseworkService.assignUserToHousework(saved.getId(), userWhoIsRated.getId());
        houseworkService.finishHousework(saved.getId());

        HouseworkRating rating1 = new HouseworkRating();
        rating1.setRate(1);
        rating1.setComment("Bad");
        housework1.addRateToHousework(rating1);

        houseworkService.rateHousework(saved.getId(), userWhoRate.getId(), rating1);
    }


    private void checkUserRateAvg() {
        User user = userService.getByEmail("user0@wp.pl");
        Double rate = userService.getRating(user.getId());

        assertEquals(rate, Double.valueOf(3));
    }
}


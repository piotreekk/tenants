package pl.piotrek.tenants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.service.HouseService;
import pl.piotrek.tenants.service.HouseworkService;
import pl.piotrek.tenants.service.UserService;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AssignUsersIntegrationTest {
    public static final String U1_EMAIL = "user1@email.pl";
    public static final String U1_PASSWORD = "123445678";

    public static final String U2_EMAIL = "user2@email.pl";
    public static final String U2_PASSWORD = "12345678";

    public Long HOUSE_ID;
    public Long HOUSEWORK_ID;


    @Autowired
    HouseService houseService;

    @Autowired
    UserService userService;

    @Autowired
    HouseworkService houseworkService;

    @Test
    public void assignUsersTest(){
        addHouseWithInhabitants();
        addHouseworkAndAssignTwoUsers();

        Collection<User> users =  houseworkService.getHouseworkUsers(HOUSEWORK_ID);

        assertEquals(2, users.size());

    }

    private void addHouseWithInhabitants(){
        User user = new User();
        user.setEmail(U1_EMAIL);
        user.setPassword(U1_PASSWORD);

        userService.addUser(user);


        User user1 = new User();
        user1.setEmail(U2_EMAIL);
        user1.setPassword(U2_PASSWORD);

        userService.addUser(user1);

        House house = new House();
        house.addInhabitant(user);
        house.addInhabitant(user1);

        House saved = houseService.addHouse(house);

        HOUSE_ID = saved.getId();
    }

    private void addHouseworkAndAssignTwoUsers(){
        User user1 = userService.getUserByEmail(U1_EMAIL);
        User user2 = userService.getUserByEmail(U2_EMAIL);

        House house = houseService.getHouseById(HOUSE_ID);

        Housework housework = new Housework();
        housework.setHouse(house);

        Housework saved1 = houseworkService.addHousework(housework);

        houseworkService.assignUserToHousework(saved1.getId(), user1.getId());
        houseworkService.assignUserToHousework(saved1.getId(), user2.getId());

        HOUSEWORK_ID = saved1.getId();
    }

}

package pl.piotrek.tenants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotrek.tenants.model.House;
import pl.piotrek.tenants.model.Housework;
import pl.piotrek.tenants.model.HouseworkRating;
import pl.piotrek.tenants.model.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TenantsApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void entitiesWellModeled(){
        User user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");

        House house = new House();
        house.setCity("Lublin");
        house.setAddress("Akademicka 9");

        // relationship between user and house
        Set<House> houses = new HashSet<>();
        houses.add(house);
        user.setHouses(houses);
        Housework housework = new Housework();
        housework.setName("Sprzanie Kuchni");
        housework.setDescription("Mycie podlogi, naczynia, zlew, kuchenka, kurze itd.");
        housework.setDate(LocalDate.now());

        // relationship between housework and house
        housework.setHouse(house);

        // relationship between housework and user
        Set<User> users = new HashSet<>();
        users.add(user);
        housework.setUsers(users);

        HouseworkRating houseworkRating = new HouseworkRating();
        // relationship between rating and housework
        houseworkRating.setHousework(housework);
        // relationship between rating and user
        houseworkRating.setUser(user);



    }

}


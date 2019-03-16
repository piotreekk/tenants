package pl.piotrek.tenants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.repository.UserRepository;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HouseRepositoryTest {
    public static final String ADDRES_1 = "Some address with number 1";
    public static final String NOT_MATTER_VALUE = "name";
    public static final String ADDRESS_2 = "Hohohohoho";
    public static final String EMAIL1 = "email1234@wp.pl";
    public static final String EMAIL2 = "email123@wp.pl";
    public static final String EMAIL3 = "emailnotfromhouse@wp.pl";
    private Long houseId;

    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findInhabitantsTest(){
        addHouseWithInhabitant();
        findInhabitants(1);

        addOtherInhabitant();
        findInhabitants(2);

        addOtherInhabitantToOtherHouse();
        findInhabitants(2);
    }

    private void addHouseWithInhabitant(){
        User user = new User();
        user.setEmail(EMAIL1);
        user.setPassword(NOT_MATTER_VALUE);
        user.setFirstName(NOT_MATTER_VALUE);
        user.setLastName(NOT_MATTER_VALUE);

        User saved = userRepository.save(user);

        House house = new House();
        house.setCity(NOT_MATTER_VALUE);
        house.setAddress(ADDRES_1);
        house.addInhabitant(saved);

        House savedHouse = houseRepository.save(house);
        houseId = savedHouse.getId();

    }

    private void addOtherInhabitant(){
        User user = new User();
        user.setEmail(EMAIL2);
        user.setPassword(NOT_MATTER_VALUE);
        user.setFirstName(NOT_MATTER_VALUE);
        user.setLastName(NOT_MATTER_VALUE);

        User saved = userRepository.save(user);

        House house = houseRepository.findByAddress(ADDRES_1).get();
        house.addInhabitant(saved);

        houseRepository.save(house);

    }

    private void addOtherInhabitantToOtherHouse(){
        User user = new User();
        user.setEmail(EMAIL3);
        user.setPassword(NOT_MATTER_VALUE);
        user.setFirstName(NOT_MATTER_VALUE);
        user.setLastName(NOT_MATTER_VALUE);

        User saved = userRepository.save(user);

        House house = new House();
        house.setAddress(ADDRESS_2);
        house.setCity(NOT_MATTER_VALUE);
        house.addInhabitant(saved);

        houseRepository.save(house);

    }

    private void findInhabitants(long expected){
        Collection<User> result = houseRepository.findInhabitantsByHouseId(houseId);
        long x = result
                .stream()
                .count();

        assertEquals(expected, x);
    }

}

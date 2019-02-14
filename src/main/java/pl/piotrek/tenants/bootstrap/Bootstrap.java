package pl.piotrek.tenants.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.entity.House;
import pl.piotrek.tenants.entity.Housework;
import pl.piotrek.tenants.entity.User;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.repository.HouseworkRepository;
import pl.piotrek.tenants.repository.UserRepository;
import pl.piotrek.tenants.util.HouseworkStatus;

import java.time.LocalDate;


@Component
public class Bootstrap implements CommandLineRunner {
    private HouseRepository houseRepository;
    private UserRepository userRepository;
    private HouseworkRepository houseworkRepository;

    public Bootstrap(HouseRepository houseRepository, UserRepository userRepository, HouseworkRepository houseworkRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.houseworkRepository = houseworkRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addUser();
        addHouseForUser();
        addSingleHouse();
        addHousework();
    }

    private void addUser(){
        User user = new User();
        user.setFirstName("Piotr");
        user.setLastName("Xoxoxoox");
        userRepository.save(user);

    }

    private void addHouseForUser(){
        House house = new House();
        house.setCity("City");
        house.setAddress("Street and House Number");

        houseRepository.save(house);

        User user = userRepository.findById(1L).get();

        house.addInhabitant(user);
//        user.addHouse(house);

        houseRepository.save(house);
    }

    private void addSingleHouse(){
        House house = new House();
        house.setCity("Lublin");
        house.setAddress("DeepStreet 2");

        houseRepository.save(house);

    }

    private void addHousework(){
        User user = userRepository.findById(1L).get();
        House house = houseRepository.findById(1L).get();

        Housework housework = new Housework();
        housework.setDate(LocalDate.now());
        housework.setDescription("Clean up the kitchen");
        housework.setHouse(house);
        housework.getUsers().add(user);
        housework.setStatus(HouseworkStatus.TO_DO);

        houseworkRepository.save(housework);


    }
}

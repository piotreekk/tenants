package pl.piotrek.tenants.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.entity.House;
import pl.piotrek.tenants.entity.User;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.repository.UserRepository;



@Component
public class Bootstrap implements CommandLineRunner {
    private HouseRepository houseRepository;
    private UserRepository userRepository;

    public Bootstrap(HouseRepository houseRepository, UserRepository userRepository) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addUser();
        addHouseForUser();
//        addSingleHouse();
    }

    private void addUser(){
        User user = new User();
        user.setFirstName("Piotr");
        user.setLastName("Xoxoxoox");
        userRepository.save(user);

    }

    private void addHouseForUser(){
        House house = new House();
        house.setCity("Lublin");
        house.setAddress("Akademicka 9");

        house = houseRepository.save(house);

        User user = userRepository.findById(1L).get();
        user.getHouses().add(house);

        userRepository.save(user);

        house.getInhabitants().add(user);

        houseRepository.save(house);
    }

    private void addSingleHouse(){
        House house = new House();
        house.setCity("Lublin");
        house.setAddress("DeepStreet 2");

        houseRepository.save(house);

    }
}

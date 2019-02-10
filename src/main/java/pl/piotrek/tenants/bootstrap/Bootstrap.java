package pl.piotrek.tenants.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.model.House;
import pl.piotrek.tenants.repository.HouseRepository;

@Component
public class Bootstrap implements CommandLineRunner {
    private HouseRepository houseRepository;

    public Bootstrap(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        House house = new House();
        house.setCity("Lublin");
        house.setAddress("Akademicka 9");

        House house1 = new House();
        house.setCity("Lublin");
        house.setAddress("Gleboka 22");

        houseRepository.save(house);
        houseRepository.save(house1);
    }
}

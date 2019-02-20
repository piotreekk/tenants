package pl.piotrek.tenants.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.piotrek.tenants.model.RoleName;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.Role;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.repository.HouseworkRepository;
import pl.piotrek.tenants.repository.RoleRepository;
import pl.piotrek.tenants.repository.UserRepository;
import pl.piotrek.tenants.util.HouseworkStatus;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Component
@Transactional
// transactional umozliwia mi zastosowanie lazy initialization w User
public class Bootstrap implements CommandLineRunner {
    private HouseRepository houseRepository;
    private UserRepository userRepository;
    private HouseworkRepository houseworkRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public Bootstrap(HouseRepository houseRepository, UserRepository userRepository, HouseworkRepository houseworkRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
        this.houseworkRepository = houseworkRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        addUser();
        addHouseForUser();
        addSingleHouse();
        addHousework();
        addRoles();
    }

    private void addRoles(){
        Role userRole = new Role();
        userRole.setName(RoleName.ROLE_USER);

        Role adminRole = new Role();
        adminRole.setName(RoleName.ROLE_ADMIN);

        roleRepository.saveAll(List.of(userRole, adminRole));
    }

    private void addUser(){
        User user = new User();
        user.setFirstName("Piotr");
        user.setLastName("Xoxoxoox");
        user.setEmail("piotrek@wp.pl");

        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);

    }

    private void addHouseForUser(){
        House house = new House();
        house.setCity("City");
        house.setAddress("Street and House Number");

        houseRepository.save(house);

        User user = userRepository.findById(1L).get();

        user.getHouses().size();
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
        housework.setScheduledDate(LocalDate.now());
        housework.setDescription("Clean up the kitchen");
        housework.setHouse(house);
        housework.getUsers().add(user);
        housework.setStatus(HouseworkStatus.TO_DO);

        houseworkRepository.save(housework);
    }
}

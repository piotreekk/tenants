package pl.piotrek.tenants.service;

import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.User;

import java.util.Collection;

public interface HouseService {
    House getById(Long id);

    House getByAddress(String address);

    Collection<House> getAll();

    House addHouse(House house);

    Collection<House> getUserHouses(Long id);

    void delete(Long id);

    Collection<User> getInhabitantsOf(Long houseId);
}

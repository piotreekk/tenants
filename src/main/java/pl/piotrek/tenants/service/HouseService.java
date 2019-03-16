package pl.piotrek.tenants.service;

import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.User;

import java.util.Collection;

public interface HouseService {
    House getHouseById(Long id);

    House getHouseByAddress(String address);

    Collection<House> getAllHouses();

    House addHouse(House house);

    Collection<House> getUserHouses(Long userId);

    void delete(Long id);

    Collection<User> getHouseInhabitants(Long houseId);
}

package pl.piotrek.tenants.service;

import pl.piotrek.tenants.model.entity.House;

import java.util.List;

public interface HouseService {
    House getById(Long id);

    House getByAddress(String address);

    List<House> getAll();

    House addHouse(House house);

    List<House> getUserHouses(Long id);

    void delete(Long id);
}

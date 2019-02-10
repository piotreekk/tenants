package pl.piotrek.tenants.service;

import pl.piotrek.tenants.model.House;

import java.util.List;

public interface HouseService {
    House getById(Long id);

    House getByAddress(String address);

    List<House> getAll();

    House addHouse(House house);
}

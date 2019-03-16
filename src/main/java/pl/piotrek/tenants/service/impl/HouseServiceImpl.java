package pl.piotrek.tenants.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrek.tenants.exception.ResourceNotFoundException;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.User;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.service.HouseService;

import java.util.Collection;

@Service
public class HouseServiceImpl implements HouseService {
    private HouseRepository houseRepository;

    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public House getHouseById(Long id) {
        return houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("House", "id", id));
    }

    @Override
    public House getHouseByAddress(String address) {
        return houseRepository.findByAddress(address)
                .orElseThrow(() -> new ResourceNotFoundException("House", "address", address));
    }

    @Override
    public Collection<House> getAllHouses() {
        return houseRepository.findAll();
    }

    @Override
    public Collection<House> getUserHouses(Long userId) {
        return houseRepository.findAllByInhabitantsId(userId);
    }

    @Override
    public House addHouse(House house) {
        return houseRepository.save(house);
    }

    @Override
    public void delete(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public Collection<User> getHouseInhabitants(Long houseId) {
       return houseRepository.findInhabitantsByHouseId(houseId);
    }
}

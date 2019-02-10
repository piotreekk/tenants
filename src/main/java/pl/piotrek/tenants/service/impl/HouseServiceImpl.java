package pl.piotrek.tenants.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrek.tenants.model.House;
import pl.piotrek.tenants.repository.HouseRepository;
import pl.piotrek.tenants.service.HouseService;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {
    private HouseRepository houseRepository;

    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public House getById(Long id) {
        return houseRepository.findById(id).get();
    }

    @Override
    public House getByAddress(String address) {
        return houseRepository.findByAddress(address);
    }

    @Override
    public List<House> getAll() {
        return houseRepository.findAll();
    }

    @Override
    public House addHouse(House house) {
        return houseRepository.save(house);
    }
}

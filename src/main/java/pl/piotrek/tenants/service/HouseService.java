package pl.piotrek.tenants.service;

import org.springframework.stereotype.Service;
import pl.piotrek.tenants.model.House;

import java.util.List;

@Service
public interface HouseService {
    House getById(Long id);

    House getByAddress(String address);

    List<House> getAll();

}

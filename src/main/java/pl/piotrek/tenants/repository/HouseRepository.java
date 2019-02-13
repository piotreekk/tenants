package pl.piotrek.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrek.tenants.entity.House;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    House findByAddress(String address);
    List<House> findAllByInhabitantsId(Long id);
}

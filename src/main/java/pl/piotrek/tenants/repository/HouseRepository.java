package pl.piotrek.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrek.tenants.model.House;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

}

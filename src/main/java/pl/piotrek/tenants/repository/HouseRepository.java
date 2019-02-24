package pl.piotrek.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.piotrek.tenants.model.entity.House;
import pl.piotrek.tenants.model.entity.User;

import java.util.Collection;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    House findByAddress(String address);

    Collection<House> findAllByInhabitantsId(Long id);

    @Query("SELECT u FROM House h JOIN h.inhabitants u WHERE h.id = :houseId")
    Collection<User> findInhabitantsByHouseId(@Param("houseId") Long houseId);

}

package pl.piotrek.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrek.tenants.entity.Housework;

import java.util.List;

@Repository
public interface HouseworkRepository extends JpaRepository<Housework, Long> {

    List<Housework> findAllByHouseId(Long houseId);
}

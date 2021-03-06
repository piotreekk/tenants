package pl.piotrek.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.piotrek.tenants.model.entity.Housework;
import pl.piotrek.tenants.model.entity.User;

import java.util.List;

@Repository
public interface HouseworkRepository extends JpaRepository<Housework, Long> {

    List<Housework> findAllByHouseId(Long houseId);

    @Query("SELECT AVG(r.rate) FROM Housework h JOIN h.ratings r WHERE h.id = ?1")
    Double getRatingAvg(Long houseworkId);

    @Query("SELECT u FROM Housework h JOIN h.users u WHERE h.id = ?1")
    List<User> findUsersAssignedToHousework(Long houseworkId);
}

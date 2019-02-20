package pl.piotrek.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrek.tenants.model.entity.Housework;


@Repository
public interface HouseworkRatingRepository extends JpaRepository<Housework, Long> {
}

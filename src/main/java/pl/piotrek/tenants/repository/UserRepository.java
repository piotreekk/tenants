package pl.piotrek.tenants.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.piotrek.tenants.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}

package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT User u FROM Student JOIN Employer JOIN Manager WHERE u.email = ?1")
    Optional<User> findByEmail(String email);
}

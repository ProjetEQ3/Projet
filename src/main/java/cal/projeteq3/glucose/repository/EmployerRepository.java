package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.user.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
    Optional<Employer> findByCredentialsEmail(String email);
    Optional<Employer> findByJobOffers_id(Long id);
    Optional<Employer> findFirstByFirstNameAndLastName(String firstName, String lastName);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.credentials.email = ?1")
    boolean existsByEmail(String email);
}


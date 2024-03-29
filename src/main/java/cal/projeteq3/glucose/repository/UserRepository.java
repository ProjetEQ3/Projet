package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT c FROM Credentials c WHERE c.email = ?1")
	Optional<Credentials> findCredentials(String email);
	Optional<User> findUserByCredentialsEmail(String email);
	List<User> findAllByCredentialsRole(Role role);
}

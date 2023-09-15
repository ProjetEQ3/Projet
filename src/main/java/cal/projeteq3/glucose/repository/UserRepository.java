package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User<?>, Long>{
			User<?> findByEmail(String email);
}

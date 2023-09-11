package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    // query methods here

}

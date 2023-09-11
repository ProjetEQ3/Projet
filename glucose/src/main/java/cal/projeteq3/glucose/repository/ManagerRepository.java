package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Gestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Gestionnaire, Long> {

    // query methods here

}

package cal.projeteq3.glucose.repositories;

import cal.projeteq3.glucose.domain.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeurRepository extends JpaRepository<Employeur, Long> {

    // query methods here

}

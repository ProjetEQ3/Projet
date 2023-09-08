package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Employeur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeurRepository extends JpaRepository<Employeur, Long> {

    // query methods here

}
